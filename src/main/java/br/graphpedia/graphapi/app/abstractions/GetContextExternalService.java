package br.graphpedia.graphapi.app.abstractions;


import br.graphpedia.graphapi.core.entity.TermContext;

public interface GetContextExternalService {
    TermContext execute(String term);

}
