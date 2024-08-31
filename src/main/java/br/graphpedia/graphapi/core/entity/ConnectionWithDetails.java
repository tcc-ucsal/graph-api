package br.graphpedia.graphapi.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionWithDetails {
    private ConnectionWith connection;
    private Integer connectionsCount;
}
