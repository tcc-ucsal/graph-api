package br.graphpedia.graphapi.infra.database.neo4j.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Node("Term")
public class TermEntity {

    @Id
    @GeneratedValue
    private String id;

    @Property("title")
    private String title;

    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime updatedDate;

    @Relationship(type = "CONNECTION_WITH", direction = Relationship.Direction.OUTGOING)
    private Set<ConnectionWithEntity> connectionWiths;

    public TermEntity(String id, String title, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.title = title;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

}
