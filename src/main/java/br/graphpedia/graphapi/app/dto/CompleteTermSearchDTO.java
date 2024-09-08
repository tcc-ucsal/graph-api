package br.graphpedia.graphapi.app.dto;

import java.util.List;

public record CompleteTermSearchDTO(List<SimpleConnectionWithDTO> connections, String article) {
}
