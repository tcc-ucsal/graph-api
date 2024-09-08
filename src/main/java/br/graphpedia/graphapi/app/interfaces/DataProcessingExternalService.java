package br.graphpedia.graphapi.app.interfaces;

import br.graphpedia.graphapi.app.dto.CompleteTermSearchDTO;

public interface DataProcessingExternalService {
    CompleteTermSearchDTO getCompleteTerm(String term);
}
