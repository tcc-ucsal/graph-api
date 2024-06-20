package br.graphpedia.graphapi.core.usecase;

import br.graphpedia.graphapi.core.entity.Term;

import java.util.List;

public interface TermUseCase {

    Term create(Term term);

    List<Term> findAll();

    void deleteAll();
}
