package br.graphpedia.graphapi.infra.dataprocessing.impl;

import br.graphpedia.graphapi.app.abstractions.GetContextExternalService;
import br.graphpedia.graphapi.core.entity.TermContext;
import br.graphpedia.graphapi.app.exceptions.ExternalApiException;
import br.graphpedia.graphapi.infra.dataprocessing.dto.GetTermDPResponse;
import br.graphpedia.graphapi.infra.dataprocessing.mapper.GetContextDataProcessingApiResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
            GetTermDPResponse response = restTemplate.getForObject(getUrl("/full_text/" + term), GetTermDPResponse.class);

            return GetContextDataProcessingApiResponseMapper.INSTANCE.toTermContextDTO(response);

        }catch (HttpClientErrorException | HttpServerErrorException e ) {
            throw new ExternalApiException("Data-processing-error: " + e.getStatusCode() + " - " + e.getMessage(), e);

        } catch (Exception e) {
            throw new ExternalApiException("Data-processing-error: " + e.getMessage(), e);
        }
    }
}
