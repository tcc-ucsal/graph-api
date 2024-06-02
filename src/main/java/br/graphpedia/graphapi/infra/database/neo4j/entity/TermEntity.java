package br.graphpedia.graphapi.infra.database.neo4j.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Node("Term")
public class TermEntity {

    @Id
    @GeneratedValue
    private String id;

    @Property("title")
    private String title;
    @Property("description")
    private String description;
    @Property("source")
    private String source;

    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime updatedDate;

    @Relationship(type = "CONNECTION_WITH", direction = Relationship.Direction.OUTGOING)
    private Set<ConnectionWithEntity> connectionWiths;


}
