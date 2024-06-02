package br.graphpedia.graphapi.infra.controller;

import br.graphpedia.graphapi.core.entity.Term;
import br.graphpedia.graphapi.core.usecase.TermUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/term")
public class TermController {

    private TermUseCase termUseCase;

    @Autowired
    public TermController(TermUseCase termUseCase) {
        this.termUseCase = termUseCase;
    }

    @PostMapping
    public ResponseEntity<Term> create(@RequestBody Term term){
        return ResponseEntity.ok().body(termUseCase.create(term));
    }

    @GetMapping
    public ResponseEntity<Term> findAll(){
      return null;
    }
}
