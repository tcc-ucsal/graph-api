package br.graphpedia.graphapi.infra.controller;

import br.graphpedia.graphapi.app.TestPopulationService;
import br.graphpedia.graphapi.core.entity.TermContext;
import br.graphpedia.graphapi.core.usecase.GetGraphUseCase;
import br.graphpedia.graphapi.core.usecase.GetTermContextUseCase;
import br.graphpedia.graphapi.infra.controller.mapper.TermContextResponseMapper;
import br.graphpedia.graphapi.infra.controller.mapper.TermResponseMapper;
import br.graphpedia.graphapi.infra.controller.responses.TermContextResponse;
import br.graphpedia.graphapi.infra.controller.responses.TermResponse;
import br.graphpedia.graphapi.infra.tools.NodePositionTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/test/term")
public class TestController {

    private TestPopulationService testPopulationService;

    @Autowired
    public TestController(TestPopulationService testPopulationService) {
        this.testPopulationService = testPopulationService;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping
    public void getGraph(){
        testPopulationService.execute();
    }

    @GetMapping("/list")
    public ResponseEntity<List<String>> getNames(){
        return ResponseEntity.ok(testPopulationService.listMockNames());
    }
}
