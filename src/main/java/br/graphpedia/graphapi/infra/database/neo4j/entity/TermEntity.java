package br.graphpedia.graphapi.infra.database.neo4j.entity;

import br.graphpedia.graphapi.core.entity.Connection;
import br.graphpedia.graphapi.core.entity.Term;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Node
public class TermEntity {

    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    private UUID id;
    private String title;
    private String description;
    private String source;

    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime updatedDate;

    @Relationship(type = "CONNECTION", direction = Relationship.Direction.OUTGOING)
    private Set<Connection> connections = new HashSet<>();

    public void addConnection(Term term, Integer level) {
        this.connections.add(new Connection(UUID.randomUUID(), level, term));
    }
}
