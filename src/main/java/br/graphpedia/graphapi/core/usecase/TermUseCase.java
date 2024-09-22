package br.graphpedia.graphapi.core.usecase;

import br.graphpedia.graphapi.core.entity.Term;
import br.graphpedia.graphapi.core.entity.TermContext;

import java.util.List;
import java.util.Optional;

public interface TermUseCase {

    Term getGraph(String term);

    List<String> verifyNeedForContext(String termTitle);

    Optional<TermContext> getContextByTitle(String term);

}
