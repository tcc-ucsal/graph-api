package br.graphpedia.graphapi.infra.dataprocessing;

import br.graphpedia.graphapi.core.entity.Term;
import br.graphpedia.graphapi.core.usecase.DataProcessingUseCase;
import org.springframework.stereotype.Component;

import java.util.List;
//TODO: IMPL
@Component
public class DataProcessingImpl implements DataProcessingUseCase {
    @Override
    public List<String> getTermContext(String term) {
        //return List.of("Manga (Fruta)", "Manga (MG)", "Manga (Camisa)", "Manga (Fut)");
        return null;
    }

    @Override
    public Term getCompleteTerm(String term) {
        return null;
    }
}
