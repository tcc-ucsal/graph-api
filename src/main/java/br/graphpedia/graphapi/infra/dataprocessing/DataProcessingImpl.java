package br.graphpedia.graphapi.infra.dataprocessing;

import br.graphpedia.graphapi.app.dto.CompleteTermSearchDTO;
import br.graphpedia.graphapi.app.interfaces.DataProcessingExternalService;
import br.graphpedia.graphapi.core.exceptions.ExternalApiException;
import br.graphpedia.graphapi.core.exceptions.PersistenceException;
import br.graphpedia.graphapi.infra.dataprocessing.dto.GetTermDataProcessingApiResponse;
import br.graphpedia.graphapi.infra.dataprocessing.dto.SearchOptionsDataProcessingApiResponse;
import br.graphpedia.graphapi.infra.dataprocessing.mapper.GetTermDataProcessingApiResponseMapper;
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
import java.util.Objects;

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

    @Override
    public CompleteTermSearchDTO getCompleteTerm(String term){
        try {
            GetTermDataProcessingApiResponse data = restTemplate.getForObject(getUrl("/highlight/" + term), GetTermDataProcessingApiResponse.class);

            return GetTermDataProcessingApiResponseMapper.INSTANCE.toCompleteTermSearchDTO(data);

        } catch (HttpClientErrorException | HttpServerErrorException  e ) {
            throw new ExternalApiException("Data-processing-error: " + e.getStatusCode() + " - " + e.getMessage(), e);

        } catch (Exception e) {
            throw new ExternalApiException("Data-processing-error: " + e.getMessage(), e);
        }
    }

    @Override
    public List<String> getSearchOptions(String term) {
        SearchOptionsDataProcessingApiResponse data = restTemplate.getForObject(getUrl("/get_search_options/" + term + "/4"), SearchOptionsDataProcessingApiResponse.class);

        return Objects.isNull(data) ? List.of() :  data.result();
    }


    public CompleteTermSearchDTO getCompleteTermTest(String term) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            return GetTermDataProcessingApiResponseMapper.INSTANCE
                    .toCompleteTermSearchDTO(objectMapper.readValue(new File("src/main/resources/computerTermMock.json"),
                            GetTermDataProcessingApiResponse.class));
        }catch (Exception e){
            throw new PersistenceException(e.getMessage());
        }
    }
}
