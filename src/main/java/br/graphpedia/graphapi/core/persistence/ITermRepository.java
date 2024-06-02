package br.graphpedia.graphapi.core.persistence;

import br.graphpedia.graphapi.core.entity.Term;

import java.util.List;

public interface ITermRepository {

    Term create(Term term);

    List<Term> findAll();
}
