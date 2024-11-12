package br.graphpedia.graphapi.infra.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionWithResponse {
    private Integer relevanceLevel;
    private SimpleTermResponse term;
}
