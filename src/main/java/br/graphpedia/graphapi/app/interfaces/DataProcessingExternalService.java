package br.graphpedia.graphapi.app.interfaces;

import br.graphpedia.graphapi.app.dto.CompleteTermSearchDTO;

import java.util.List;

public interface DataProcessingExternalService {
    CompleteTermSearchDTO getCompleteTerm(String term);

    List<String> getSearchOptions(String term);
}
