package br.graphpedia.graphapi.infra.dataprocessing;

import br.graphpedia.graphapi.app.dto.CompleteTermSearchDTO;
import br.graphpedia.graphapi.app.interfaces.DataProcessingExternalService;
import br.graphpedia.graphapi.core.entity.Term;
import br.graphpedia.graphapi.core.exceptions.ExternalApiException;
import br.graphpedia.graphapi.core.exceptions.PersistenceException;
import br.graphpedia.graphapi.infra.dataprocessing.dto.DataProcessingApiResponse;
import br.graphpedia.graphapi.infra.dataprocessing.mapper.DataProcessingApiResponseMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.util.List;

@Component
public class DataProcessingImpl implements DataProcessingExternalService {

    @Value("${data-processing.base-url}")
    private String DATA_PROCESSING_URL;
    private final RestTemplate restTemplate;

    @Autowired
    public DataProcessingImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private String getUrl(String endpoint){
        return UriComponentsBuilder.fromHttpUrl(DATA_PROCESSING_URL)
                .path(endpoint)
                .toUriString();
    }

    public List<String> getTermContext(String term) {
        //return List.of("Manga (Fruta)", "Manga (MG)", "Manga (Camisa)", "Manga (Fut)");
        return null;
    }

    @Override
    public CompleteTermSearchDTO getCompleteTerm(String term){
        try {
            //DataProcessingApiResponse data = restTemplate.getForObject(getUrl("/highlight/" + term), DataProcessingApiResponse.class);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            DataProcessingApiResponse data = objectMapper.readValue(new File("src/main/resources/mocks/computerTermMock.json"),
                    DataProcessingApiResponse.class);

            return DataProcessingApiResponseMapper.INSTANCE.toCompleteTermSearchDTO(data);

        } catch (HttpClientErrorException | HttpServerErrorException  e ) {
            throw new ExternalApiException("Data-processing-error: " + e.getStatusCode() + " - " + e.getMessage(), e);

        } catch (Exception e) {
            throw new ExternalApiException("Data-processing-error: " + e.getMessage(), e);
        }
    }


    public CompleteTermSearchDTO getCompleteTermTest(String term) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            return DataProcessingApiResponseMapper.INSTANCE
                    .toCompleteTermSearchDTO(objectMapper.readValue(new File("src/main/resources/computerTermMock.json"),
                            DataProcessingApiResponse.class));
        }catch (Exception e){
            throw new PersistenceException(e.getMessage());
        }
    }
}
