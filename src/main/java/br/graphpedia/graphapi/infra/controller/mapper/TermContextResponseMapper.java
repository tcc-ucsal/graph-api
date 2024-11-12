package br.graphpedia.graphapi.infra.controller.mapper;

import br.graphpedia.graphapi.core.entity.TermContext;
import br.graphpedia.graphapi.infra.controller.response.TermContextResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TermContextResponseMapper {
    TermContextResponseMapper INSTANCE = Mappers.getMapper(TermContextResponseMapper.class);

    @Mapping(target = "searchedAt", expression = "java(entity.getUpdatedDate() == null ? entity.getCreatedDate() : entity.getUpdatedDate())")
    TermContextResponse toResponse(TermContext entity);

}
