package br.graphpedia.graphapi.app;

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

import java.util.List;
import java.util.Optional;

@Service
public class TermService implements TermUseCase {

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

        //todo: resolver problema de query de verificação
        Optional<TermContext> termContext = contextTermRepository.findByTitleOrSynonyms(term);

        if(termContext.isPresent()){
            graph = getGraphLevels(term);
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

    private Term getGraphLevels(String term) {
        //TODO: FAZER FUNÇÃO DE BUSCA DO GRAFO, PENSANDO EM NOS POR NIVEL
        Term graph = new Term();
        graph.setTitle(term);
        return graph;
    }

    @Override
    public Term createTest(Term term) {
        Term createdTerm = structTermRepository.create(term);
        return createdTerm;
    }

    @Override
    public List<Term> findAll() {
        return structTermRepository.findAll();
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


}
