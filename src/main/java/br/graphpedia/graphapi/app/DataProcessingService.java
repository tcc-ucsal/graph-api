package br.graphpedia.graphapi.app;

import br.graphpedia.graphapi.app.dto.CompleteTermSearchDTO;
import br.graphpedia.graphapi.app.interfaces.DataProcessingExternalService;
import br.graphpedia.graphapi.app.mapper.ConnectionWithMapper;
import br.graphpedia.graphapi.core.entity.Term;
import br.graphpedia.graphapi.core.entity.TermContext;
import br.graphpedia.graphapi.core.usecase.DataProcessingUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class DataProcessingService implements DataProcessingUseCase {

    private final DataProcessingExternalService dataProcessingExternalService;

    @Autowired
    public DataProcessingService(@Lazy DataProcessingExternalService dataProcessingExternalService) {
        this.dataProcessingExternalService = dataProcessingExternalService;
    }

    @Override
    public List<String> getTermContext(String term) {
        //todo: impl
        return null;
    }

    @Override
    public Term getCompleteTerm(String term) {
        CompleteTermSearchDTO result = dataProcessingExternalService.getCompleteTerm(term);

        Term termResponse = new Term(result.searched_term());
        TermContext context = new TermContext(result.searched_term(), result.article(), result.source());

        termResponse.setConnectionWiths(Set.copyOf(ConnectionWithMapper.INSTANCE.convertSimpleConnectionWithDTOtoCore(result.connections())));
        termResponse.setContext(context);

        return termResponse;
    }
}
