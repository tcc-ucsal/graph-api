package br.graphpedia.graphapi.core.persistence;

import br.graphpedia.graphapi.core.entity.TermContext;

import java.util.Optional;


public interface IContextTermRepository {

    TermContext save(TermContext context);

    Optional<TermContext> findByTitleOrSynonyms(String term);

}
