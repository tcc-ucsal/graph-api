package br.graphpedia.graphapi.core.persistence;

import br.graphpedia.graphapi.core.entity.ConnectionWith;
import br.graphpedia.graphapi.app.dto.ConnectionWithCountDTO;
import br.graphpedia.graphapi.core.entity.Term;

import java.util.List;

public interface IStructTermRepository {

    void create(Term term);
    List<ConnectionWithCountDTO> getConnectionsWithLevelOneCount(String title);
    void deleteByTitleIfNotIncomingConnections(String title);
    List<ConnectionWith> getConnectionByLevel(String[] titles, int level, int limit);
}
