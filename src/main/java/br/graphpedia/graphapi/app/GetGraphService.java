package br.graphpedia.graphapi.app;

import br.graphpedia.graphapi.app.dto.ConnectionWithCountDTO;
import br.graphpedia.graphapi.app.mapper.ConnectionWithMapper;
import br.graphpedia.graphapi.core.entity.ConnectionWith;
import br.graphpedia.graphapi.core.entity.Term;
import br.graphpedia.graphapi.core.entity.TermContext;
import br.graphpedia.graphapi.core.exceptions.PersistenceException;
import br.graphpedia.graphapi.core.persistence.IContextTermRepository;
import br.graphpedia.graphapi.core.persistence.IStructTermRepository;
import br.graphpedia.graphapi.core.usecase.GetCompleteSearchUseCase;
import br.graphpedia.graphapi.core.usecase.GetGraphUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        Optional<Term> optTerm = getExistingGraph(term);
        if (optTerm.isPresent()) return optTerm.get();

        Term graph;
        graph = getCompleteSearchUseCase.execute(term);

        if(!term.equalsIgnoreCase(graph.getTitle())){
            graph.getContext().addSynonyms(term);
        }

        try{
            TermContext createdContext = contextTermRepository.save(graph.getContext());
            structTermRepository.create(graph);
            graph.setContext(createdContext);

        }catch (Exception exception){
            contextTermRepository.deleteByTitle(graph.getTitle());
            structTermRepository.deleteByTitleIfNotIncomingConnections(graph.getTitle());
            throw new PersistenceException("Error on save graph", exception);
        }

        return graph;
    }

    private Optional<Term> getExistingGraph(String term) {
        Optional<TermContext> termContext = contextTermRepository.findByTitleOrSynonyms(term);

        if(termContext.isPresent()){
            Term graph = new Term(termContext.get().getTitle());
            graph.setConnectionWiths(getMaxTermsOnScreen(term));
            graph.setContext(termContext.get());
            return Optional.of(graph);
        }
        return Optional.empty();
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
