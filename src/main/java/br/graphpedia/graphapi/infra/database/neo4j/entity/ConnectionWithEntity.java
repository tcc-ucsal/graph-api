package br.graphpedia.graphapi.infra.database.neo4j.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@RelationshipProperties
public class ConnectionWithEntity {

    @Id
    @GeneratedValue
    private String id;

    @Property("relevance_level")
    private Integer relevanceLevel;

    @TargetNode
    private TermEntity targetTerm;
}
