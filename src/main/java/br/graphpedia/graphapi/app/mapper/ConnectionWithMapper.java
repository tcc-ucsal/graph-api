package br.graphpedia.graphapi.app.mapper;

import br.graphpedia.graphapi.app.dto.ConnectionWithDTO;
import br.graphpedia.graphapi.app.dto.SimpleConnectionWithDTO;
import br.graphpedia.graphapi.core.entity.ConnectionWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
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
    @Mapping(target = "targetTerm.title", source = "term")
    ConnectionWith convertSimpleConnectionWithDTOtoCore(SimpleConnectionWithDTO simpleConnectionWithDTO);

    default List<ConnectionWith> convertSimpleConnectionWithDTOtoCore(String mainTitle, List<SimpleConnectionWithDTO> simpleConnectionWithDTOs){
        List<ConnectionWith> result = new ArrayList<>();

        simpleConnectionWithDTOs.forEach(s -> {
            ConnectionWith con = convertSimpleConnectionWithDTOtoCore(s);
            con.setMainTitle(mainTitle);
            result.add(con);
        });

        return result;
    }

}
