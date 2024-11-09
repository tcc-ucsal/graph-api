package br.graphpedia.graphapi.core.usecase;

import br.graphpedia.graphapi.core.entity.Term;

public interface GetGraphUseCase {
    int MAX_SCREEN_NODES = 50;
    Term execute(String term);

}
