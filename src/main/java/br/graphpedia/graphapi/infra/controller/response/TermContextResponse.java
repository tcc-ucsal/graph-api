package br.graphpedia.graphapi.infra.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TermContextResponse {
    private String id;
    private String title;
    private String article;
    private String source;
    private LocalDateTime searchedAt;
}
