package br.graphpedia.graphapi.app;

import br.graphpedia.graphapi.core.entity.Term;
import br.graphpedia.graphapi.core.persistence.ITermRepository;
import br.graphpedia.graphapi.core.usecase.TermUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TermService implements TermUseCase {

    private final ITermRepository iTermRepository;

    @Autowired
    public TermService(ITermRepository iTermRepository) {
        this.iTermRepository = iTermRepository;
    }

    @Override
    public Term create(Term term) {
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
