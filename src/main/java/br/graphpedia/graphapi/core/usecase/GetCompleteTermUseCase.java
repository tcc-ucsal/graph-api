package br.graphpedia.graphapi.core.usecase;

import br.graphpedia.graphapi.core.entity.Term;

public interface GetCompleteTermUseCase {
    Term execute(String term);

}
