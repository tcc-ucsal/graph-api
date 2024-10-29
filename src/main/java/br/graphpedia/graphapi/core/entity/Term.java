package br.graphpedia.graphapi.core.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Term {
    private String id;
    private String title;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Set<ConnectionWith> connectionWiths = new HashSet<>();
    private TermContext context;

    public Term(String title) {
        this.title = title;
    }

    public void setConnectionWiths(Set<ConnectionWith> connectionWiths) {
        this.connectionWiths = connectionWiths;
        this.connectionWiths = this.connectionWiths.stream()
                .sorted(Comparator.comparing(ConnectionWith::getRelevanceLevel))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
