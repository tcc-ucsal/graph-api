package br.graphpedia.graphapi.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Term {
    private UUID id;
    private String title;
    private String description;
    private String source;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Set<Connection> connections = new HashSet<>();

    public void addConnection(Term term, Integer level) {
        this.connections.add(new Connection(UUID.randomUUID(), level, term));
    }

}
