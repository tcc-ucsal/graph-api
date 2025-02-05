package br.graphpedia.graphapi.app.services;

import br.graphpedia.graphapi.app.interfaces.GetSearchOptionsExternalService;
import br.graphpedia.graphapi.core.usecase.VerifyNeedForContextUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VerifyNeedForContextService implements VerifyNeedForContextUseCase {

    private final GetSearchOptionsExternalService getSearchOptionsExternalService;

    @Autowired
    public VerifyNeedForContextService(GetSearchOptionsExternalService getSearchOptionsExternalService) {
        this.getSearchOptionsExternalService = getSearchOptionsExternalService;
    }

    @Override
    public List<String> execute(String termTitle) {
        return getSearchOptionsExternalService.execute(termTitle);
    }
}
