package br.graphpedia.graphapi.core.persistence;

import br.graphpedia.graphapi.core.entity.Term;

public interface ITermRepository {

    Term create(Term term);
}
