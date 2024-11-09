package br.graphpedia.graphapi.infra.controller.mapper;

import br.graphpedia.graphapi.core.entity.ConnectionWith;
import br.graphpedia.graphapi.core.entity.Term;
import br.graphpedia.graphapi.core.entity.TermContext;
import br.graphpedia.graphapi.infra.controller.responses.ConnectionWithResponse;
import br.graphpedia.graphapi.infra.controller.responses.Coordinates;
import br.graphpedia.graphapi.infra.controller.responses.TermContextResponse;
import br.graphpedia.graphapi.infra.controller.responses.TermResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Map;
import java.util.Set;

@Mapper
public interface TermResponseMapper {
    TermResponseMapper INSTANCE = Mappers.getMapper(TermResponseMapper.class);

    @Mapping(target = "id", expression = "java(entity.getId())")
    @Mapping(target = "title", expression = "java(entity.getTitle())")
    @Mapping(target = "connectionWiths", expression = "java(getConnectionWithResponses(entity.getConnectionWiths(), coordinates))")
    @Mapping(target = "context", expression = "java(getTermContextResponse(entity.getContext()))")
    @Mapping(target = "coordinates", expression = "java(getTermCoordinates(entity.getTitle(), coordinates))")
    TermResponse toResponseWithCoordinates(Term entity, Map<String, Coordinates> coordinates);


    default Coordinates getTermCoordinates(String title, Map<String, Coordinates> coordinates){
        return coordinates.get(title);
    }

    default Set<ConnectionWithResponse> getConnectionWithResponses(Set<ConnectionWith> connection, Map<String, Coordinates> coordinates){
        return connection == null ? null : ConnectionWithResponseMapper.INSTANCE.toResponseWithCoordinates(connection,coordinates);
    }

    default TermContextResponse getTermContextResponse(TermContext context){
        return context == null ? null : TermContextResponseMapper.INSTANCE.toResponse(context);

    }



}
