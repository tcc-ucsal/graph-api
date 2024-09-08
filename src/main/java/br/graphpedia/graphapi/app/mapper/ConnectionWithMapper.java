package br.graphpedia.graphapi.app.mapper;

import br.graphpedia.graphapi.app.dto.ConnectionWithDTO;
import br.graphpedia.graphapi.app.dto.SimpleConnectionWithDTO;
import br.graphpedia.graphapi.core.entity.ConnectionWith;
import br.graphpedia.graphapi.core.entity.Term;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

public interface ConnectionWithMapper {

    ConnectionWithMapper INSTANCE = Mappers.getMapper(ConnectionWithMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "mainTitle", target = "mainTitle")
    @Mapping(source = "relevanceLevel", target = "relevanceLevel")
    @Mapping(source = "targetTerm", target = "targetTerm")
    ConnectionWith convertConnectionWithDTOtoCore(ConnectionWithDTO connectionWithDTO);

    List<ConnectionWith> convertConnectionWithDTOtoCore(List<ConnectionWithDTO> connectionWithDTOs);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "mainTitle", ignore = true)
    @Mapping(source = "level", target = "relevanceLevel")
    @Mapping(target = "targetTerm", expression = "java(getTerm(simpleConnectionWithDTO.term))")
    ConnectionWith convertSimpleConnectionWithDTOtoCore(SimpleConnectionWithDTO simpleConnectionWithDTO);

    List<ConnectionWith> convertSimpleConnectionWithDTOtoCore(List<SimpleConnectionWithDTO> simpleConnectionWithDTOs);

    default Term getTerm(String title){
        return new Term(title);
    }
}
