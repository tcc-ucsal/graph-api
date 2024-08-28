package br.graphpedia.graphapi.infra.controller;

import br.graphpedia.graphapi.core.entity.Term;
import br.graphpedia.graphapi.core.entity.TermContext;
import br.graphpedia.graphapi.core.usecase.TermUseCase;
import br.graphpedia.graphapi.infra.controller.mapper.TermContextResponseMapper;
import br.graphpedia.graphapi.infra.controller.responses.TermContextResponse;
import br.graphpedia.graphapi.infra.controller.responses.TermResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/term")
public class TermController {

    private final TermUseCase termUseCase;

    @Autowired
    public TermController(TermUseCase termUseCase) {
        this.termUseCase = termUseCase;
    }

    @PostMapping("/test/create")
    public ResponseEntity<Term> createTest(@RequestBody Term term){
        return ResponseEntity.ok().body(termUseCase.createTest(term));
    }

    @GetMapping("/{term}")
    public ResponseEntity<Term> getGraph(@PathVariable String term){
        return ResponseEntity.ok().body(termUseCase.getGraph(term));
    }

    @GetMapping("/context/{term}")
    public ResponseEntity<TermContextResponse> getContext(@PathVariable String term){
        Optional<TermContext> termContext = termUseCase.getContextByTitle(term);
        return termContext.map(context ->
                        ResponseEntity.ok().body(TermContextResponseMapper.INSTANCE.toResponse(context)))
                            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/")
    public ResponseEntity<Term> findAll(){
      return null;
    }

    @DeleteMapping("/")
    public ResponseEntity<Object> deleteAll(){
        termUseCase.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
