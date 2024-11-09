package br.graphpedia.graphapi.app.abstractions;

import br.graphpedia.graphapi.app.dto.CompleteTermSearchDTO;

public interface GetCompleteTermExternalService {
    CompleteTermSearchDTO execute(String term);

}
