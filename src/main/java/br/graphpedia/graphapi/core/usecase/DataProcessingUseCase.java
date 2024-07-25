package br.graphpedia.graphapi.core.usecase;

import br.graphpedia.graphapi.core.entity.Term;

import java.util.List;

public interface DataProcessingUseCase {

    List<String> getTermContext(String term);

    Term getCompleteTerm(String term);
}
