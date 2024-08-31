package br.graphpedia.graphapi.core.persistence;

import br.graphpedia.graphapi.core.entity.Term;

import java.util.List;

public interface IStructTermRepository {

    Term create(Term term);
    void deleteAll();
    void deleteByTitleIfNotIncomingConnections(String title);
}
