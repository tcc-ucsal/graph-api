package br.graphpedia.graphapi.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Connection {

    private UUID id;

    private Integer relevanceLevel;

    private Term targetTerm;


}
