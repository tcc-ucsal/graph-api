package br.graphpedia.graphapi.infra.database.neo4j.mapper;

import br.graphpedia.graphapi.core.entity.ConnectionWith;
import br.graphpedia.graphapi.core.entity.Term;
import br.graphpedia.graphapi.infra.database.neo4j.entity.ConnectionWithEntity;
import br.graphpedia.graphapi.infra.database.neo4j.entity.TermEntity;

import java.util.*;
import java.util.Collections;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TermNeo4jMapper {

    TermNeo4jMapper INSTANCE = Mappers.getMapper(TermNeo4jMapper.class);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "source", target = "source"),
            @Mapping(source = "createdDate", target = "createdDate"),
            @Mapping(source = "updatedDate", target = "updatedDate"),
            @Mapping(target = "connectionWiths", expression = "java(connectionsToCore(termEntity.getConnectionWiths()))")
    })
    Term toTermCore(TermEntity termEntity);
    List<Term> toTermCore(List<TermEntity> entities);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "source", target = "source"),
            @Mapping(source = "createdDate", target = "createdDate"),
            @Mapping(source = "updatedDate", target = "updatedDate"),
            @Mapping(target = "connectionWiths", expression = "java(connectionsToEntity(term.getConnectionWiths()))")
    })
    TermEntity toTermEntity(Term term);
    List<TermEntity> toTermEntity(List<Term> term);

    default Set<ConnectionWithEntity> connectionsToEntity(Set<ConnectionWith> connections){
        if(Objects.isNull(connections) || connections.isEmpty())
            return Collections.emptySet();

        return ConnectionWithNeo4jMapper.INSTANCE.toConnectionWithEntity(connections);
    }

    default Set<ConnectionWith> connectionsToCore(Set<ConnectionWithEntity> connections){
        if(Objects.isNull(connections) || connections.isEmpty())
            return Collections.emptySet();

        return ConnectionWithNeo4jMapper.INSTANCE.toConnectionWithCore(connections);
    }
}
