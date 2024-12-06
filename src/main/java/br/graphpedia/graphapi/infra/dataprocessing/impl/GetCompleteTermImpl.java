package br.graphpedia.graphapi.infra.dataprocessing.impl;

import br.graphpedia.graphapi.app.dto.CompleteTermSearchDTO;
import br.graphpedia.graphapi.app.interfaces.GetCompleteTermExternalService;
import br.graphpedia.graphapi.core.exceptions.ExternalApiException;
import br.graphpedia.graphapi.core.exceptions.ResourceNotFoundException;
import br.graphpedia.graphapi.infra.dataprocessing.dto.GetTermDPResponse;
import br.graphpedia.graphapi.infra.dataprocessing.mapper.GetTermDataProcessingApiResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import static br.graphpedia.graphapi.infra.dataprocessing.tools.DataProcessingUtils.getUrl;

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
            ResponseEntity<GetTermDPResponse> data = restTemplate.getForEntity(getUrl("/highlight/" + term), GetTermDPResponse.class);

            return GetTermDataProcessingApiResponseMapper.INSTANCE.toCompleteTermSearchDTO(data.getBody());

        } catch (HttpClientErrorException | HttpServerErrorException e ) {
            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND)){
                throw new ResourceNotFoundException("Data-processing-error: 404 - Article not found");
            }

            throw new ExternalApiException("Data-processing-error: " + e.getStatusCode() + " - " + e.getMessage(), e);

        } catch (Exception e) {
            throw new ExternalApiException("Data-processing-error: " + e.getMessage(), e);
        }
    }
}
