package br.graphpedia.graphapi.core.usecase;

import br.graphpedia.graphapi.core.entity.Term;

import java.util.List;

public interface TermUseCase {

    Term getGraph(String term);

    Term createTest(Term term);

    List<Term> findAll();

    void deleteAll();

    List<String> getSynonymTerms(String termTitle);
}
