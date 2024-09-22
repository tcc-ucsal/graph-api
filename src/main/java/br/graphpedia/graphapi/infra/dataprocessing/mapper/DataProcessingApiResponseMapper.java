package br.graphpedia.graphapi.infra.dataprocessing.mapper;

import br.graphpedia.graphapi.app.dto.CompleteTermSearchDTO;
import br.graphpedia.graphapi.infra.dataprocessing.dto.GetTermDataProcessingApiResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DataProcessingApiResponseMapper {
    DataProcessingApiResponseMapper INSTANCE = Mappers.getMapper(DataProcessingApiResponseMapper.class);

    @Mapping(source = "nodes", target = "connections")
    @Mapping(source = "article", target = "article")
    CompleteTermSearchDTO toCompleteTermSearchDTO(GetTermDataProcessingApiResponse getTermDataProcessingApiResponse);

}
