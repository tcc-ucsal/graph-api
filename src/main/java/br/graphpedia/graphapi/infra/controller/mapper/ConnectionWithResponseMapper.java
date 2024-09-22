package br.graphpedia.graphapi.infra.controller.mapper;

import br.graphpedia.graphapi.core.entity.ConnectionWith;
import br.graphpedia.graphapi.infra.controller.responses.ConnectionWithResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper
public interface ConnectionWithResponseMapper {
    ConnectionWithResponseMapper INSTANCE = Mappers.getMapper(ConnectionWithResponseMapper.class);

    @Mapping(target = "term.title", source = "targetTerm.title")
    @Mapping(target = "term.connectionWiths", source = "targetTerm.connectionWiths")
    ConnectionWithResponse toResponse(ConnectionWith entity);

    Set<ConnectionWithResponse> toResponse(Set<ConnectionWith> entity);

}
