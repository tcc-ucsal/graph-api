package br.graphpedia.graphapi.app;

import br.graphpedia.graphapi.core.entity.Term;
import br.graphpedia.graphapi.core.persistence.IStructTermRepository;
import br.graphpedia.graphapi.core.usecase.DataProcessingUseCase;
import br.graphpedia.graphapi.core.usecase.TermUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TermService implements TermUseCase {

    private final IStructTermRepository iStructTermRepository;

    private final DataProcessingUseCase dataProcessingUseCase;

    @Autowired
    public TermService(IStructTermRepository iStructTermRepository, DataProcessingUseCase dataProcessingUseCase) {
        this.iStructTermRepository = iStructTermRepository;
        this.dataProcessingUseCase = dataProcessingUseCase;
    }

    @Override
    public Term getGraph(String term) {

        Term graph = getGraphLevels(term);

        //TODO: CRIAR METODO PARA VERIFICAR SE A ARVORE ESTA COMPLETA OU SE PRECISA DE NOVAS CONSULTAS

        if(Objects.nonNull(graph))
            return graph;

        return null;
    }

    private Term getGraphLevels(String term) {
        //TODO: FAZER FUNÇÃO DE BUSCA DO GRAFO, PENSANDO EM NOS POR NIVEL
        return null;
    }

    @Override
    public Term createTest(Term term) {
        Term createdTerm = iStructTermRepository.create(term);
        return createdTerm;
    }

    @Override
    public List<Term> findAll() {
        return iStructTermRepository.findAll();
    }

    @Override
    public void deleteAll() {
        iStructTermRepository.deleteAll();
    }

    @Override
    public List<String> verifyNeedForContext(String termTitle) {
        //TODO: IMPL
        return dataProcessingUseCase.getTermContext(termTitle);
    }


}
