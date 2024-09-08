package br.graphpedia.graphapi.app;

import br.graphpedia.graphapi.app.dto.CompleteTermSearchDTO;
import br.graphpedia.graphapi.app.interfaces.DataProcessingExternalService;
import br.graphpedia.graphapi.app.mapper.ConnectionWithMapper;
import br.graphpedia.graphapi.core.entity.Term;
import br.graphpedia.graphapi.core.usecase.DataProcessingUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class DataProcessingService implements DataProcessingUseCase {

    private final DataProcessingExternalService dataProcessingExternalService;

    @Autowired
    public DataProcessingService(DataProcessingExternalService dataProcessingExternalService) {
        this.dataProcessingExternalService = dataProcessingExternalService;
    }

    @Override
    public List<String> getTermContext(String term) {
        return null;
    }

    @Override
    public Term getCompleteTerm(String term) {
        CompleteTermSearchDTO result = dataProcessingExternalService.getCompleteTerm(term);

        //todo: ajustar mapeamento de context
        Term termResponse = new Term(term);
        termResponse.setConnectionWiths(Set.copyOf(ConnectionWithMapper.INSTANCE.convertSimpleConnectionWithDTOtoCore(result.connections())));
        //termResponse.setContext();
        return termResponse;
    }
}
