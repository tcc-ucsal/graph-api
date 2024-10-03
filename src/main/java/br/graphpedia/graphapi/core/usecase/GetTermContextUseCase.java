package br.graphpedia.graphapi.core.usecase;

import br.graphpedia.graphapi.core.entity.TermContext;

import java.util.Optional;

public interface GetTermContextUseCase {
    Optional<TermContext> getByTitle(String term);

}
