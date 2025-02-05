package br.graphpedia.graphapi.infra.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TermResponse {
    private String id;
    private String title;
    private Coordinates coordinates;
    private Set<ConnectionWithResponse> connectionWiths = new HashSet<>();
    private TermContextResponse context;
}
