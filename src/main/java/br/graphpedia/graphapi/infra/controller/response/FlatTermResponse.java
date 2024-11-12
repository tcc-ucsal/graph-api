package br.graphpedia.graphapi.infra.controller.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlatTermResponse extends Coordinates {
    private String id;
    private String title;
    private String parentTitle;
    private Integer relevanceLevel;
}
