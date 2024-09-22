package br.graphpedia.graphapi.infra.controller.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleTermResponse {
    private String title;
    private Set<ConnectionWithResponse> connectionWiths;
}
