package br.graphpedia.graphapi.core.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionWith {
    private String id;
    private Integer relevanceLevel;
    private Term targetTerm;
}
