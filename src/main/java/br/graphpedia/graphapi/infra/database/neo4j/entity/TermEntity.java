package br.graphpedia.graphapi.infra.database.neo4j.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.neo4j.core.schema.*;

import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@Builder
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

    public TermEntity() {
    }

    public TermEntity(String id, String title, String description, String source, LocalDateTime createdDate, LocalDateTime updatedDate, Set<ConnectionWithEntity> connectionWiths) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.source = source;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.connectionWiths = connectionWiths;
    }

    public TermEntity(String id, String title, String description, String source, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.source = source;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

}
