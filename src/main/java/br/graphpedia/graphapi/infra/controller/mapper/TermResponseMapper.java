package br.graphpedia.graphapi.infra.controller.mapper;

import br.graphpedia.graphapi.core.entity.ConnectionWith;
import br.graphpedia.graphapi.core.entity.Term;
import br.graphpedia.graphapi.core.entity.TermContext;
import br.graphpedia.graphapi.infra.controller.responses.ConnectionWithResponse;
import br.graphpedia.graphapi.infra.controller.responses.TermContextResponse;
import br.graphpedia.graphapi.infra.controller.responses.TermResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper
public interface TermResponseMapper {
    TermResponseMapper INSTANCE = Mappers.getMapper(TermResponseMapper.class);

    @Mapping(target = "connectionWiths", expression = "java(getConnectionWithResponses(entity.getConnectionWiths()))")
    @Mapping(target = "context", expression = "java(getTermContextResponse(entity.getContext()))")
    TermResponse toResponse(Term entity);

    default Set<ConnectionWithResponse> getConnectionWithResponses(Set<ConnectionWith> connection){
        return connection == null ? null : ConnectionWithResponseMapper.INSTANCE.toResponse(connection);
    }

    default TermContextResponse getTermContextResponse(TermContext context){
        return context == null ? null : TermContextResponseMapper.INSTANCE.toResponse(context);

    }



}
