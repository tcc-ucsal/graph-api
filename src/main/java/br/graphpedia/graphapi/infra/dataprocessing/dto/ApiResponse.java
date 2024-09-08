package br.graphpedia.graphapi.infra.dataprocessing.dto;

import br.graphpedia.graphapi.app.dto.SimpleConnectionWithDTO;

import java.util.List;

public record ApiResponse(List<SimpleConnectionWithDTO> node, String article) {}
