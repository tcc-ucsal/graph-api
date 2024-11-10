package br.graphpedia.graphapi.app;

import br.graphpedia.graphapi.app.abstractions.GetContextExternalService;
import br.graphpedia.graphapi.app.dto.TermContextDTO;
import br.graphpedia.graphapi.core.entity.TermContext;
import br.graphpedia.graphapi.core.persistence.IContextTermRepository;
import br.graphpedia.graphapi.core.usecase.GetTermContextUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetTermContextService implements GetTermContextUseCase {

    private final IContextTermRepository contextTermRepository;
    private final GetContextExternalService getContextExternalService;

    @Autowired
    public GetTermContextService(IContextTermRepository contextTermRepository, GetContextExternalService getContextExternalService) {
        this.contextTermRepository = contextTermRepository;
        this.getContextExternalService = getContextExternalService;
    }

    @Override
    public Optional<TermContext> getByTitle(String term) {
        Optional<TermContext> result = contextTermRepository.findByTitleOrSynonyms(term);

        if(result.isEmpty()){
            result = Optional.ofNullable(getContextExternalService.execute(term));
            if(result.isPresent()){
                TermContext context = result.get();
                context.setSearched(false);
                contextTermRepository.save(context);
            }
        }

        return result;
    }
}
