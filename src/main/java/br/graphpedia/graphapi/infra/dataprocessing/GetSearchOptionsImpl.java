package br.graphpedia.graphapi.infra.dataprocessing;

import br.graphpedia.graphapi.app.interfaces.GetSearchOptionsExternalService;
import br.graphpedia.graphapi.infra.dataprocessing.dto.SearchOptionsDataProcessingApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

import static br.graphpedia.graphapi.infra.dataprocessing.DataProcessingUtils.getUrl;

@Component
public class GetSearchOptionsImpl implements GetSearchOptionsExternalService {
    private final RestTemplate restTemplate;

    @Autowired
    public GetSearchOptionsImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @Override
    public List<String> execute(String term) {
        SearchOptionsDataProcessingApiResponse data = restTemplate.getForObject(getUrl("/get_search_options/" + term + "/4"), SearchOptionsDataProcessingApiResponse.class);

        return Objects.isNull(data) ? List.of() :  data.result();
    }
}
