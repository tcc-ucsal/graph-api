package br.graphpedia.graphapi.core.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Term {
    private String id;
    private String title;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private boolean completed;
    private Set<ConnectionWith> connectionWiths = new HashSet<>();
}
