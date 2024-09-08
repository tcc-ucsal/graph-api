package br.graphpedia.graphapi.app.dto;

import br.graphpedia.graphapi.core.entity.Term;

public record ConnectionWithDTO(String id, String mainTitle, Integer relevanceLevel, Term targetTerm) {
}
