package br.graphpedia.graphapi.infra.dataprocessing.mapper;

import br.graphpedia.graphapi.core.entity.TermContext;
import br.graphpedia.graphapi.infra.dataprocessing.dto.GetTermDPResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GetContextDataProcessingApiResponseMapper {
    GetContextDataProcessingApiResponseMapper INSTANCE = Mappers.getMapper(GetContextDataProcessingApiResponseMapper.class);

    @Mapping(target = "title", source = "searched_term")
    TermContext toTermContextDTO(GetTermDPResponse response);

}
