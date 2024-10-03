package br.graphpedia.graphapi.infra.dataprocessing;

import br.graphpedia.graphapi.app.dto.CompleteTermSearchDTO;
import br.graphpedia.graphapi.app.interfaces.GetCompleteTermExternalService;
import br.graphpedia.graphapi.core.exceptions.ExternalApiException;
import br.graphpedia.graphapi.infra.dataprocessing.dto.GetTermDataProcessingApiResponse;
import br.graphpedia.graphapi.infra.dataprocessing.mapper.GetTermDataProcessingApiResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import static br.graphpedia.graphapi.infra.dataprocessing.DataProcessingUtils.getUrl;

@Component
public class GetCompleteTermImpl implements GetCompleteTermExternalService {
    private final RestTemplate restTemplate;

    @Autowired
    public GetCompleteTermImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @Override
    public CompleteTermSearchDTO execute(String term) {
        try {
            GetTermDataProcessingApiResponse data = restTemplate.getForObject(getUrl("/highlight/" + term), GetTermDataProcessingApiResponse.class);

            return GetTermDataProcessingApiResponseMapper.INSTANCE.toCompleteTermSearchDTO(data);

        } catch (HttpClientErrorException | HttpServerErrorException e ) {
            throw new ExternalApiException("Data-processing-error: " + e.getStatusCode() + " - " + e.getMessage(), e);

        } catch (Exception e) {
            throw new ExternalApiException("Data-processing-error: " + e.getMessage(), e);
        }
    }
}
