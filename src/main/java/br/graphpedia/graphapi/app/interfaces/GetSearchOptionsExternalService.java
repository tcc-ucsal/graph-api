package br.graphpedia.graphapi.app.interfaces;

import java.util.List;

public interface GetSearchOptionsExternalService {
    List<String> execute(String term);

}
