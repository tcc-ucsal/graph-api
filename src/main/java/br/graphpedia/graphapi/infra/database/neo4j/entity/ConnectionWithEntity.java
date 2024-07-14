package br.graphpedia.graphapi.infra.database.neo4j.entity;

import lombok.*;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

@Setter
@Getter
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

}
