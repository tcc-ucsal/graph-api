package br.graphpedia.graphapi.core.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Term {
    private String id;
    private String title;
    private String source;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Set<ConnectionWith> connectionWiths = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Set<ConnectionWith> getConnectionWiths() {
        return connectionWiths;
    }

    public void setConnectionWiths(Set<ConnectionWith> connectionWiths) {
        this.connectionWiths = connectionWiths;
    }

    public Term(String id, String title, String source, LocalDateTime createdDate, LocalDateTime updatedDate, Set<ConnectionWith> connectionWiths) {
        this.id = id;
        this.title = title;
        this.source = source;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.connectionWiths = connectionWiths;
    }

    public Term() {
    }
}
