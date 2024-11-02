package br.graphpedia.graphapi.infra.controller;

import br.graphpedia.graphapi.app.TestPopulationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
