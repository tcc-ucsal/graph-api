package br.graphpedia.graphapi.infra.controller;

import br.graphpedia.graphapi.core.entity.Term;
import br.graphpedia.graphapi.core.usecase.TermUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
