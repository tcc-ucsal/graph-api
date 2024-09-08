package br.graphpedia.graphapi.infra.dataprocessing.mapper;

import br.graphpedia.graphapi.app.dto.CompleteTermSearchDTO;
import br.graphpedia.graphapi.infra.dataprocessing.dto.ApiResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DataProcessingApiResponseMapper {
    DataProcessingApiResponseMapper INSTANCE = Mappers.getMapper(DataProcessingApiResponseMapper.class);

    @Mapping(source = "node", target = "connections")
    @Mapping(source = "article", target = "article")
    CompleteTermSearchDTO toCompleteTermSearchDTO(ApiResponse apiResponse);

}
