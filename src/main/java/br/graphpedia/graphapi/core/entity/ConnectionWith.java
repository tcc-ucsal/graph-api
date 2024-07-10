package br.graphpedia.graphapi.core.entity;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ConnectionWith {

    private String id;

    private Integer relevanceLevel;

    private Term targetTerm;

    public ConnectionWith(String id, Integer relevanceLevel, Term targetTerm) {
        this.id = id;
        this.relevanceLevel = relevanceLevel;
        this.targetTerm = targetTerm;
    }

    public ConnectionWith() {
    }

}
