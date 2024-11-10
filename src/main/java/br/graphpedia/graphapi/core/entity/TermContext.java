package br.graphpedia.graphapi.core.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TermContext {
    private String id;
    private String title;
    private String article;
    private String source;
    private boolean searched;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private List<String> synonyms;

    public TermContext(String title, String article, String source) {
        this.title = title;
        this.article = article;
        this.source = source;
    }

    public void addSynonyms(String synonym){
        if(Objects.isNull(synonyms))
            setSynonyms(new ArrayList<>());

        getSynonyms().add(synonym);
    }

}
