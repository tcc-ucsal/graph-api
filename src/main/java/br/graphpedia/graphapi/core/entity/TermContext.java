package br.graphpedia.graphapi.core.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TermContext {
    private String id;
    private String title;
    private String article;
    private String source;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private List<String> synonyms;

    public TermContext(String title, String article, String source) {
        this.title = title;
        this.article = article;
        this.source = source;
    }
}
