package br.graphpedia.graphapi.app.dto;

import br.graphpedia.graphapi.core.entity.ConnectionWith;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public record ConnectionWithCountDTO(ConnectionWithDTO connection, Integer connectionsCount) {
}
