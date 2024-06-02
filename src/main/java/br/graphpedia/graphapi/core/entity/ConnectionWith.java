package br.graphpedia.graphapi.core.entity;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class ConnectionWith {

    private UUID id;

    private Integer relevanceLevel;

    private Term targetTerm;

    public ConnectionWith(UUID id, Integer relevanceLevel, Term targetTerm) {
        this.id = id;
        this.relevanceLevel = relevanceLevel;
        this.targetTerm = targetTerm;
    }

    public ConnectionWith() {
    }
}
