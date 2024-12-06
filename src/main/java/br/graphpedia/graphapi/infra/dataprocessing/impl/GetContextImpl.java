package br.graphpedia.graphapi.infra.dataprocessing.impl;

import br.graphpedia.graphapi.app.interfaces.GetContextExternalService;
import br.graphpedia.graphapi.core.entity.TermContext;
import br.graphpedia.graphapi.core.exceptions.ExternalApiException;
import br.graphpedia.graphapi.core.exceptions.ResourceNotFoundException;
import br.graphpedia.graphapi.infra.dataprocessing.dto.GetTermDPResponse;
import br.graphpedia.graphapi.infra.dataprocessing.mapper.GetContextDataProcessingApiResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import static br.graphpedia.graphapi.infra.dataprocessing.tools.DataProcessingUtils.getUrl;

@Component
public class GetContextImpl implements GetContextExternalService {

    private final RestTemplate restTemplate;

    @Autowired
    public GetContextImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public TermContext execute(String term) {
        try{
            ResponseEntity<GetTermDPResponse> response = restTemplate.getForEntity(getUrl("/full_text/" + term), GetTermDPResponse.class);

            return GetContextDataProcessingApiResponseMapper.INSTANCE.toTermContextDTO(response.getBody());

        }catch (HttpClientErrorException | HttpServerErrorException e ) {

            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND)){
                throw new ResourceNotFoundException("Data-processing-error: 404 - Article not found");
            }

            throw new ExternalApiException("Data-processing-error: " + e.getStatusCode() + " - " + e.getMessage(), e);

        } catch (Exception e) {
            throw new ExternalApiException("Data-processing-error: " + e.getMessage(), e);
        }
    }
}
