package br.graphpedia.graphapi.core.usecase.test;

import java.util.List;

public interface TestPopulationUseCase {
    void execute();

    List<String> listMockNames();
}
