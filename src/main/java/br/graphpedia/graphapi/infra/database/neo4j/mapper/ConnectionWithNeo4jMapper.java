package br.graphpedia.graphapi.infra.database.neo4j.mapper;

import br.graphpedia.graphapi.core.entity.ConnectionWith;
import br.graphpedia.graphapi.infra.database.neo4j.entity.ConnectionWithEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper
public interface ConnectionWithNeo4jMapper {

    ConnectionWithNeo4jMapper INSTANCE = Mappers.getMapper(ConnectionWithNeo4jMapper.class);

    @Mapping(source = "targetTerm", target = "targetTerm")
    ConnectionWith toConnectionWithCore(ConnectionWithEntity connectionWithEntity);
    Set<ConnectionWith> toConnectionWithCore(Set<ConnectionWithEntity> connectionWithEntity);

    @Mapping(source = "targetTerm", target = "targetTerm")
    ConnectionWithEntity toConnectionWithEntity(ConnectionWith connectionWith);
    Set<ConnectionWithEntity> toConnectionWithEntity(Set<ConnectionWith> connectionWith);

}
