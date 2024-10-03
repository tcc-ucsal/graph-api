package br.graphpedia.graphapi.core.usecase;

import java.util.List;

public interface VerifyNeedForContextUseCase {
    List<String> execute(String termTitle);

}
