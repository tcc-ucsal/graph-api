package br.graphpedia.graphapi.core.entity;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getRelevanceLevel() {
        return relevanceLevel;
    }

    public void setRelevanceLevel(Integer relevanceLevel) {
        this.relevanceLevel = relevanceLevel;
    }

    public Term getTargetTerm() {
        return targetTerm;
    }

    public void setTargetTerm(Term targetTerm) {
        this.targetTerm = targetTerm;
    }
}
