package br.graphpedia.graphapi.infra.dataprocessing.tools;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class DataProcessingUtils {

    private static String DATA_PROCESSING_URL;

    @Value("${data-processing.base-url}")
    public void setDataProcessingUrl(String url) {
        DATA_PROCESSING_URL = url;
    }

    public static String getUrl(String endpoint){
        return UriComponentsBuilder.fromHttpUrl(DATA_PROCESSING_URL)
                .path(endpoint)
                .toUriString();
    }
}
