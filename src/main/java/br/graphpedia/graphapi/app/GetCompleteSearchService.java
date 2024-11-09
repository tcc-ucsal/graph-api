package br.graphpedia.graphapi.app;

import br.graphpedia.graphapi.app.dto.CompleteTermSearchDTO;
import br.graphpedia.graphapi.app.abstractions.GetCompleteTermExternalService;
import br.graphpedia.graphapi.app.mapper.ConnectionWithMapper;
import br.graphpedia.graphapi.core.entity.Term;
import br.graphpedia.graphapi.core.entity.TermContext;
import br.graphpedia.graphapi.core.usecase.GetCompleteSearchUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class GetCompleteSearchService implements GetCompleteSearchUseCase {

    private final GetCompleteTermExternalService getCompleteTermExternalService;

    @Autowired
    public GetCompleteSearchService(GetCompleteTermExternalService getCompleteTermExternalService) {
        this.getCompleteTermExternalService = getCompleteTermExternalService;
    }

    @Override
    public Term execute(String term) {
        CompleteTermSearchDTO result = getCompleteTermExternalService.execute(term);

        Term termResponse = new Term(result.searched_term());
        TermContext context = new TermContext(result.searched_term(), result.article(), result.source());

        termResponse.setConnectionWiths(Set.copyOf(ConnectionWithMapper.INSTANCE.convertSimpleConnectionWithDTOtoCore(result.connections())));
        termResponse.setContext(context);

        return termResponse;
    }
}
