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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/test/term")
public class TestController {

    private final TestPopulationService testPopulationService;

    @Autowired
    public TestController(TestPopulationService testPopulationService) {
        this.testPopulationService = testPopulationService;
    }

    @Operation(summary = "Populate database with test cases")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Database Error",
                    content = @Content)})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping
    public void getGraph(){
        testPopulationService.execute();
    }

    @Operation(summary = "List all available terms after populating the data")
    @GetMapping("/list")
    public ResponseEntity<List<String>> getNames(){
        return ResponseEntity.ok(testPopulationService.listMockNames());
    }
}
