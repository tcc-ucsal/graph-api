package br.graphpedia.graphapi.infra.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleTermResponse {
    private String title;
    private Coordinates coordinates;
    private Set<ConnectionWithResponse> connectionWiths;
}
