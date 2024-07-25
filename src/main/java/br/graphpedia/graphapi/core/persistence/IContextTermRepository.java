package br.graphpedia.graphapi.core.persistence;

import br.graphpedia.graphapi.core.entity.Term;
import br.graphpedia.graphapi.core.entity.TermContext;

import java.util.List;

public interface IContextTermRepository {

    TermContext save(TermContext context);

    TermContext findByTitleOrSynonyms(String term);

}
