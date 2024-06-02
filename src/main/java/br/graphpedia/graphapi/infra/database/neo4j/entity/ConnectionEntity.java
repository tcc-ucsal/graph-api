package br.graphpedia.graphapi.infra.database.neo4j.entity;

import br.graphpedia.graphapi.core.entity.Term;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@RelationshipProperties
public class ConnectionEntity {
    @RelationshipId
    private UUID id;

    @Property
    private Integer relevanceLevel;

    @TargetNode
    private Term targetTerm;
}
