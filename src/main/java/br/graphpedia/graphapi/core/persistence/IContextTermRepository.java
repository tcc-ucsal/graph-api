package br.graphpedia.graphapi.core.persistence;

import br.graphpedia.graphapi.core.entity.TermContext;


public interface IContextTermRepository {

    TermContext save(TermContext context);

    TermContext findByTitleOrSynonyms(String term);

}
