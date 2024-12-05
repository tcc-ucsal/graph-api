package br.graphpedia.graphapi.app.services;

import br.graphpedia.graphapi.app.dto.ConnectionWithCountDTO;
import br.graphpedia.graphapi.app.mapper.ConnectionWithMapper;
import br.graphpedia.graphapi.core.entity.ConnectionWith;
import br.graphpedia.graphapi.core.entity.Term;
import br.graphpedia.graphapi.core.entity.TermContext;
import br.graphpedia.graphapi.core.persistence.IContextTermRepository;
import br.graphpedia.graphapi.core.persistence.IStructTermRepository;
import br.graphpedia.graphapi.core.usecase.GetCompleteSearchUseCase;
import br.graphpedia.graphapi.core.usecase.GetGraphUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GetGraphService implements GetGraphUseCase {

    private final IStructTermRepository structTermRepository;

    private final IContextTermRepository contextTermRepository;

    private final GetCompleteSearchUseCase getCompleteSearchUseCase;

    @Autowired
    public GetGraphService(IStructTermRepository structTermRepository, IContextTermRepository contextTermRepository, GetCompleteSearchUseCase getCompleteSearchUseCase) {
        this.structTermRepository = structTermRepository;
        this.contextTermRepository = contextTermRepository;
        this.getCompleteSearchUseCase = getCompleteSearchUseCase;
    }

    @Override
    public Term execute(String term) {

        Optional<TermContext> termContext = contextTermRepository.findByTitleOrSynonyms(term);

        if(termContext.isPresent() && termContext.get().isSearched()) {
            Optional<Term> optTerm = getExistingGraph(term, termContext.get());
            getNodesMaxLitmit(optTerm.get());
            return optTerm.get();
        }

        Term graph;
        graph = getCompleteSearchUseCase.execute(term);

        if(!term.equalsIgnoreCase(graph.getTitle())){
            graph.getContext().addSynonyms(term);
        }

        try{
            structTermRepository.create(graph);

            TermContext context = graph.getContext();
            if(termContext.isPresent()){
                context.setCreatedDate(termContext.get().getCreatedDate());
                context.setId(termContext.get().getId());
            }
            context.setSearched(true);
            TermContext createdContext = contextTermRepository.save(context);

            graph.setContext(createdContext);

        }catch (Exception exception){
            if(termContext.isEmpty()){
                contextTermRepository.deleteByTitle(graph.getTitle());
            }else{
                contextTermRepository.save(termContext.get());
            }
            structTermRepository.deleteByTitleIfNotIncomingConnections(graph.getTitle());
            throw exception;
        }

        getNodesMaxLitmit(graph);

        return graph;
    }

    private static void getNodesMaxLitmit(Term graph) {
        if(graph.getConnectionWiths().size() > MAX_SCREEN_NODES){
            graph.setConnectionWiths(graph.getConnectionWiths().stream().limit(MAX_SCREEN_NODES).collect(Collectors.toSet()));
        }
    }

    private Optional<Term> getExistingGraph(String term, TermContext termContext) {
        Term graph = new Term(termContext.getTitle());
        graph.setConnectionWiths(getMaxTermsOnScreen(term));
        graph.setContext(termContext);
        return Optional.of(graph);

    }

    private Set<ConnectionWith> getMaxTermsOnScreen(String term) {

        List<ConnectionWithCountDTO> connections = new ArrayList<>(structTermRepository.getConnectionsWithLevelOneCount(term));

        if(connections.size() < MAX_SCREEN_NODES){

            int limit = connections.stream()
                    .map(ConnectionWithCountDTO::connectionsCount)
                    .filter(i -> i > 0)
                    .max(Integer::compare)
                    .map(i -> Math.min(i, 2))
                    .orElse(0);

            String[] complementTerms = connections.stream()
                    .filter(c -> c.connectionsCount() > 0)
                    .map(c -> c.connection().targetTerm().getTitle())
                    .toArray(String[]::new);

            List<ConnectionWith> complements = structTermRepository.getConnectionByLevel(complementTerms, 1, limit);

            for (ConnectionWith comp : complements) {
                Optional<ConnectionWithCountDTO> connection = connections.stream()
                        .filter(c -> c.connection().targetTerm().getTitle().equals(comp.getMainTitle()))
                        .findFirst();

                if (connection.isPresent()) {
                    int indexOfMain = connections.indexOf(connection.get());
                    connection.get().connection().targetTerm().getConnectionWiths().add(comp);
                    connections.set(indexOfMain, connection.get());
                }
            }

        }

        return Set.copyOf(ConnectionWithMapper.INSTANCE.convertConnectionWithDTOtoCore(connections.stream().map(ConnectionWithCountDTO::connection).toList()));
    }
}
