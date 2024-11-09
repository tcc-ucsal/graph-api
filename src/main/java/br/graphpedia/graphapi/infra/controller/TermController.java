package br.graphpedia.graphapi.infra.controller;

import br.graphpedia.graphapi.app.abstractions.LayoutProcessor;
import br.graphpedia.graphapi.core.entity.Term;
import br.graphpedia.graphapi.core.entity.TermContext;
import br.graphpedia.graphapi.core.usecase.GetGraphUseCase;
import br.graphpedia.graphapi.core.usecase.GetTermContextUseCase;
import br.graphpedia.graphapi.infra.controller.mapper.FlatTermResponseMapper;
import br.graphpedia.graphapi.infra.controller.mapper.TermContextResponseMapper;
import br.graphpedia.graphapi.infra.controller.mapper.TermResponseMapper;
import br.graphpedia.graphapi.infra.controller.responses.Coordinates;
import br.graphpedia.graphapi.infra.controller.responses.FlatTermResponse;
import br.graphpedia.graphapi.infra.controller.responses.TermContextResponse;
import br.graphpedia.graphapi.infra.controller.responses.TermResponse;
import br.graphpedia.graphapi.infra.controller.layout.RadialLayoutProcessor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/term")
public class TermController {

    private final GetGraphUseCase getGraphUseCase;

    private final GetTermContextUseCase getTermContextUseCase;

    @Autowired
    public TermController(GetGraphUseCase getGraphUseCase, GetTermContextUseCase getTermContextUseCase) {
        this.getGraphUseCase = getGraphUseCase;
        this.getTermContextUseCase = getTermContextUseCase;
    }

    @Operation(summary = "Get Search Result as flat list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FlatTermResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Persistence Error",
                    content = @Content)})
    @GetMapping("/flat/{term}")
    public ResponseEntity<List<FlatTermResponse>> getFlatGraph(@PathVariable String term){
        Term rootEntity =getGraphUseCase.execute(term);
        Map<String, Coordinates> coordinatesMap =
                LayoutProcessor.applyLayout(new RadialLayoutProcessor(), rootEntity);
        List<FlatTermResponse> root = FlatTermResponseMapper.INSTANCE.toResponseWithCoordinates(rootEntity, coordinatesMap);
        return ResponseEntity.ok().body(root);
    }

    @Operation(summary = "Get Search Result")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TermResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Persistence Error",
                    content = @Content)})
    @GetMapping("/{term}")
    public ResponseEntity<TermResponse> getGraph(@PathVariable String term){
        Term rootEntity = getGraphUseCase.execute(term);
        Map<String, Coordinates> coordinatesMap =
                LayoutProcessor.applyLayout(new RadialLayoutProcessor(), rootEntity);
        TermResponse root = TermResponseMapper.INSTANCE.toResponseWithCoordinates(rootEntity, coordinatesMap);

        return ResponseEntity.ok().body(root);
    }

    @Operation(summary = "Get content for specific term")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TermContextResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Database Error",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Context not found",
                    content = @Content)
})
    @GetMapping("/context/{term}")
    public ResponseEntity<TermContextResponse> getContext(@PathVariable @NotNull @NotEmpty String term){
        Optional<TermContext> termContext = getTermContextUseCase.getByTitle(term);
        return termContext.map(context ->
                        ResponseEntity.ok().body(TermContextResponseMapper.INSTANCE.toResponse(context)))
                            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
