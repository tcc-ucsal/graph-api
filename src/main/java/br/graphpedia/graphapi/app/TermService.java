package br.graphpedia.graphapi.app;

import br.graphpedia.graphapi.core.entity.ConnectionWithDetails;
import br.graphpedia.graphapi.core.entity.Term;
import br.graphpedia.graphapi.core.entity.TermContext;
import br.graphpedia.graphapi.core.exceptions.PersistenceException;
import br.graphpedia.graphapi.core.persistence.IContextTermRepository;
import br.graphpedia.graphapi.core.persistence.IStructTermRepository;
import br.graphpedia.graphapi.core.usecase.DataProcessingUseCase;
import br.graphpedia.graphapi.core.usecase.TermUseCase;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service
public class TermService implements TermUseCase {

    private static final int MAX_SCREEN_NODES = 30;
    private final IStructTermRepository structTermRepository;

    private final IContextTermRepository contextTermRepository;

    private final DataProcessingUseCase dataProcessingUseCase;

    @Autowired
    public TermService(IStructTermRepository structTermRepository, IContextTermRepository contextTermRepository, DataProcessingUseCase dataProcessingUseCase) {
        this.structTermRepository = structTermRepository;
        this.contextTermRepository = contextTermRepository;
        this.dataProcessingUseCase = dataProcessingUseCase;
    }

    @Override
    @Transactional
    public Term getGraph(String term) {

        Term graph = new Term();

        Optional<TermContext> termContext = contextTermRepository.findByTitleOrSynonyms(term);

        if(termContext.isPresent()){
            graph = getMaxTermsOnScreen(term);
            graph.setContext(termContext.get());
            return graph;
        }

        graph = dataProcessingUseCase.getCompleteTerm(term);

        try{
            TermContext createdContext = contextTermRepository.save(graph.getContext());
            graph.setContext(createdContext);
            graph = structTermRepository.create(graph);

        }catch (Exception exception){
            contextTermRepository.deleteByTitle(graph.getTitle());
            structTermRepository.deleteByTitleIfNotIncomingConnections(graph.getTitle());
            throw new PersistenceException("Error on save graph", exception.getCause());
        }

        return graph;
    }

    private Term getMaxTermsOnScreen(String term) {

        List<ConnectionWithDetails> connections = structTermRepository.getConnectionsWithDetails(term);

        if(connections.size() < MAX_SCREEN_NODES){
            connections.sort(Comparator.comparingInt(
                    connectionWithDetails -> connectionWithDetails.getConnection().getRelevanceLevel()
            ));

            HashMap<String, Integer> idsCount = getTermsToComplete(connections);
            //TODO: FINALIZAR E TESTAR
            //TODO: FAZER METODO REPOSITORY PARA OBTER NUMERO CERTO DE NOS
            //TODO: MESCLAR RESULTADOS
        }

        Term graph = new Term();
        graph.setTitle(term);
        return graph;
    }

    private HashMap<String, Integer> getTermsToComplete(List<ConnectionWithDetails> connections) {
        HashMap<String,Integer> result = new HashMap<>();

        int currentSum = connections.stream().mapToInt(ConnectionWithDetails::getConnectionsCount).sum() + connections.size();
        int enableValue = MAX_SCREEN_NODES - currentSum;
        int relevanceLevel = 1;

        if(currentSum <= enableValue){
            connections.forEach(c -> result.put(c.getConnection().getId(), c.getConnectionsCount()));
        }else{
            result.putAll(getProportionalTermCount(connections, enableValue, currentSum, relevanceLevel));
        }

        return result;
    }

    private Map<String, Integer> getProportionalTermCount(List<ConnectionWithDetails> connections, int enableValue, int currentSum, int relevanceLevel) {
        Map<String, Integer> result = new HashMap<>();

        Stream<ConnectionWithDetails> currentLevelList = connections.stream()
                .filter(c -> c.getConnection().getRelevanceLevel() == relevanceLevel);

        int countCurrentLevel = currentLevelList
                .mapToInt(cc -> cc.getConnection().getRelevanceLevel()).sum();

        if(countCurrentLevel < enableValue){
            currentLevelList.forEach(c -> result.put(c.getConnection().getId(), c.getConnectionsCount()));

            int newEnableValue = enableValue - countCurrentLevel;
            int newCurrentSum = currentSum + countCurrentLevel;
            int nextRelevanceLevel = relevanceLevel+1;

            result.putAll(getProportionalTermCount(connections, newEnableValue, newCurrentSum, nextRelevanceLevel));
        }else{
            currentLevelList.forEach(c -> {
                int balanceCount = getBalance(enableValue, currentSum, c.getConnectionsCount());
                result.put(c.getConnection().getId(), balanceCount);
            });
        }

        return result;
    }

    private static int getBalance(int enableValue, int currentSum, Integer value) {
        return (int) Math.round((double) value * enableValue / currentSum);
    }

    @Override
    public void deleteAll() {
        structTermRepository.deleteAll();
    }

    @Override
    public List<String> verifyNeedForContext(String termTitle) {
        //TODO: IMPL
        return dataProcessingUseCase.getTermContext(termTitle);
    }

    @Override
    public Optional<TermContext> getContextByTitle(String term) {
        return contextTermRepository.findByTitleOrSynonyms(term);
    }

    @Override
    public Term termPrincipal(String term) {
        return null;
    }


}
