package br.graphpedia.graphapi.infra.dataprocessing;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.util.UriComponentsBuilder;

public class DataProcessingUtils {

    @Value("${data-processing.base-url}")
    private static String DATA_PROCESSING_URL;

    protected static String getUrl(String endpoint){
        return UriComponentsBuilder.fromHttpUrl(DATA_PROCESSING_URL)
                .path(endpoint)
                .toUriString();
    }
}
