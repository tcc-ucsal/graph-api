package br.graphpedia.graphapi.infra.database.elasticsearch.mapper;

import br.graphpedia.graphapi.core.entity.TermContext;
import br.graphpedia.graphapi.infra.database.elasticsearch.entity.TermContextEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TermContextElasticMapper {

    TermContextElasticMapper INSTANCE = Mappers.getMapper(TermContextElasticMapper.class);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "article", target = "article"),
            @Mapping(source = "source", target = "source"),
            @Mapping(source = "createdDate", target = "createdDate"),
            @Mapping(source = "updatedDate", target = "updatedDate"),
            @Mapping(source = "synonyms", target = "synonyms")
    })
    TermContext toTermContextCore(TermContextEntity entity);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "article", target = "article"),
            @Mapping(source = "source", target = "source"),
            @Mapping(source = "createdDate", target = "createdDate"),
            @Mapping(source = "updatedDate", target = "updatedDate"),
            @Mapping(source = "synonyms", target = "synonyms")
    })
    TermContextEntity toEntity(TermContext dto);
}
