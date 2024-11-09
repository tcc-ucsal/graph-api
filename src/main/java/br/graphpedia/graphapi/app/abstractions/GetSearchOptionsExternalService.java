package br.graphpedia.graphapi.app.abstractions;

import java.util.List;

public interface GetSearchOptionsExternalService {
    List<String> execute(String term);

}
