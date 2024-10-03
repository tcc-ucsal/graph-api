package br.graphpedia.graphapi.app;

import br.graphpedia.graphapi.core.entity.TermContext;
import br.graphpedia.graphapi.core.persistence.IContextTermRepository;
import br.graphpedia.graphapi.core.usecase.GetTermContextUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetTermContextService implements GetTermContextUseCase {

    private final IContextTermRepository contextTermRepository;

    @Autowired
    public GetTermContextService(IContextTermRepository contextTermRepository) {
        this.contextTermRepository = contextTermRepository;
    }

    @Override
    public Optional<TermContext> getByTitle(String term) {
        return contextTermRepository.findByTitleOrSynonyms(term);
    }
}
