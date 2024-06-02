package br.graphpedia.graphapi.core.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@Data
public class Term {
    private UUID id;
    private String title;
    private String description;
    private String source;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Set<ConnectionWith> connectionWiths = new HashSet<>();

    public Term(UUID id, String title, String description, String source, LocalDateTime createdDate, LocalDateTime updatedDate, Set<ConnectionWith> connectionWiths) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.source = source;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.connectionWiths = connectionWiths;
    }

    public Term() {
    }
}
