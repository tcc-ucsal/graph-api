package br.graphpedia.graphapi.infra.controller.mapper;

import br.graphpedia.graphapi.core.entity.ConnectionWith;
import br.graphpedia.graphapi.infra.controller.response.ConnectionWithResponse;
import br.graphpedia.graphapi.infra.controller.response.Coordinates;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Map;
import java.util.Set;

@Mapper
public interface ConnectionWithResponseMapper {
    ConnectionWithResponseMapper INSTANCE = Mappers.getMapper(ConnectionWithResponseMapper.class);

    @Mapping(target = "term.title", source = "targetTerm.title")
    @Mapping(target = "term.connectionWiths", source = "targetTerm.connectionWiths")
    ConnectionWithResponse toResponse(ConnectionWith entity);

    Set<ConnectionWithResponse> toResponse(Set<ConnectionWith> entity);

    default Set<ConnectionWithResponse> toResponseWithCoordinates(Set<ConnectionWith> entity, Map<String, Coordinates> coordinates){
        Set<ConnectionWithResponse> connection = toResponse(entity);
        connection.forEach(con -> con.getTerm().setCoordinates(coordinates.get(con.getTerm().getTitle())));
        return connection;
    }

}
