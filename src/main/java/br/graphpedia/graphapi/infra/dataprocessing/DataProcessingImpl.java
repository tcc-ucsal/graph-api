package br.graphpedia.graphapi.infra.dataprocessing;

import br.graphpedia.graphapi.core.entity.Term;
import br.graphpedia.graphapi.core.exceptions.PersistenceException;
import br.graphpedia.graphapi.core.usecase.DataProcessingUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

import java.io.File;
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
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            return objectMapper.readValue(new File("src/main/resources/graphMock.json"), Term.class);
        }catch (Exception e){
            throw new PersistenceException(e.getMessage());
        }
    }
}
