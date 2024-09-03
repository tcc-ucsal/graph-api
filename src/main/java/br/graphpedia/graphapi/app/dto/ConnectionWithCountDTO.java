package br.graphpedia.graphapi.app.dto;

import br.graphpedia.graphapi.core.entity.ConnectionWith;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionWithCountDTO {
    private ConnectionWith connection;
    private Integer connectionsCount;
}
