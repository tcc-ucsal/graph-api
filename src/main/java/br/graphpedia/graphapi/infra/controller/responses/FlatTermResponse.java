package br.graphpedia.graphapi.infra.controller.responses;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

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
