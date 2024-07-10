package br.graphpedia.graphapi.infra.database.neo4j.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.util.UUID;

@Builder
@RelationshipProperties
public class ConnectionWithEntity {

    @Id
    @GeneratedValue
    private String id;

    @Property("relevance_level")
    private Integer relevanceLevel;

    @TargetNode
    private TermEntity targetTerm;

    public ConnectionWithEntity() {
    }

    public ConnectionWithEntity(String id, Integer relevanceLevel, TermEntity targetTerm) {
        this.id = id;
        this.relevanceLevel = relevanceLevel;
        this.targetTerm = targetTerm;
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

    public TermEntity getTargetTerm() {
        return targetTerm;
    }

    public void setTargetTerm(TermEntity targetTerm) {
        this.targetTerm = targetTerm;
    }
}
