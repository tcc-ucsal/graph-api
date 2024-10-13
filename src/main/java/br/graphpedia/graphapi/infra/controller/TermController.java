package br.graphpedia.graphapi.infra.controller;

import br.graphpedia.graphapi.core.entity.Term;
import br.graphpedia.graphapi.core.entity.TermContext;
import br.graphpedia.graphapi.core.usecase.GetGraphUseCase;
import br.graphpedia.graphapi.core.usecase.GetTermContextUseCase;
import br.graphpedia.graphapi.infra.controller.mapper.TermContextResponseMapper;
import br.graphpedia.graphapi.infra.controller.mapper.TermResponseMapper;
import br.graphpedia.graphapi.infra.controller.responses.TermContextResponse;
import br.graphpedia.graphapi.infra.controller.responses.TermResponse;
import br.graphpedia.graphapi.infra.controller.tools.NodePositionTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{term}")
    public ResponseEntity<TermResponse> getGraph(@PathVariable String term){
        TermResponse root = TermResponseMapper.INSTANCE.toResponse(getGraphUseCase.execute(term));
        NodePositionTools.radialTreeLayout(root);
        return ResponseEntity.ok().body(root);
    }

    @GetMapping("/context/{term}")
    public ResponseEntity<TermContextResponse> getContext(@PathVariable String term){
        Optional<TermContext> termContext = getTermContextUseCase.getByTitle(term);
        return termContext.map(context ->
                        ResponseEntity.ok().body(TermContextResponseMapper.INSTANCE.toResponse(context)))
                            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
