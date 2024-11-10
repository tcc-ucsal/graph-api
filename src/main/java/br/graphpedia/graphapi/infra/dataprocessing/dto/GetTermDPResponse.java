package br.graphpedia.graphapi.infra.dataprocessing.dto;

import br.graphpedia.graphapi.app.dto.SimpleConnectionWithDTO;

import java.util.List;

public record GetTermDPResponse(List<SimpleConnectionWithDTO> nodes, String article, String source, String searched_term) {}
