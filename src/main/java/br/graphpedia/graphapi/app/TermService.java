package br.graphpedia.graphapi.app;

import br.graphpedia.graphapi.core.entity.Term;
import br.graphpedia.graphapi.core.persistence.ITermRepository;
import br.graphpedia.graphapi.core.usecase.TermUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TermService implements TermUseCase {

    private final ITermRepository iTermRepository;

    @Autowired
    public TermService(ITermRepository iTermRepository) {
        this.iTermRepository = iTermRepository;
    }

    @Override
    public Term create(Term term) {
        return null;
    }
}
