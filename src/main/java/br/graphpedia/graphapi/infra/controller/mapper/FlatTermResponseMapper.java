package br.graphpedia.graphapi.infra.controller.mapper;

import br.graphpedia.graphapi.core.entity.ConnectionWith;
import br.graphpedia.graphapi.core.entity.Term;
import br.graphpedia.graphapi.infra.controller.responses.FlatTermResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Mapper
public interface FlatTermResponseMapper {
    FlatTermResponseMapper INSTANCE = Mappers.getMapper(FlatTermResponseMapper.class);

    default List<FlatTermResponse> toResponse(Term term) {
        Set<ConnectionWith> connections = term.getConnectionWiths();

        List<FlatTermResponse> result = new ArrayList<>();

        FlatTermResponse flat = new FlatTermResponse();
        flat.setTitle(term.getTitle());
        flat.setId(term.getId());
        result.add(flat);

        getFlatChild(connections, result);
        return result;
    }

    private static void getFlatChild(Set<ConnectionWith> connections, List<FlatTermResponse> result) {
        connections.forEach(con -> {
            FlatTermResponse child = new FlatTermResponse();
            child.setId(con.getTargetTerm().getId());
            child.setTitle(con.getTargetTerm().getTitle());
            child.setParentTitle(con.getMainTitle());
            child.setRelevanceLevel(con.getRelevanceLevel());
            result.add(child);
            if(!con.getTargetTerm().getConnectionWiths().isEmpty()){
                getFlatChild(con.getTargetTerm().getConnectionWiths(), result);
            }
        });
    }
}
