package br.graphpedia.graphapi.app;

import br.graphpedia.graphapi.core.entity.Term;
import br.graphpedia.graphapi.core.persistence.ITermRepository;
import br.graphpedia.graphapi.core.usecase.TermUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TermService implements TermUseCase {

    private final ITermRepository iTermRepository;

    @Autowired
    public TermService(ITermRepository iTermRepository) {
        this.iTermRepository = iTermRepository;
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
        Term createdTerm = iTermRepository.create(term);
        return createdTerm;
    }

    @Override
    public List<Term> findAll() {
        return iTermRepository.findAll();
    }

    @Override
    public void deleteAll() {
        iTermRepository.deleteAll();
    }

    @Override
    public List<String> getSynonymTerms(String termTitle) {
        return List.of();
        //return List.of("Manga (Fruta), Manga (MG), Manga (Camisa), Manga (Fut)");
    }


}
