package br.graphpedia.graphapi.infra.database.neo4j.repository;

import br.graphpedia.graphapi.core.entity.Term;
import br.graphpedia.graphapi.core.persistence.ITermRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TermRepositoryImpl implements ITermRepository {
    private final Neo4jTermRepository neo4jTermRepository;

    @Autowired
    public TermRepositoryImpl(Neo4jTermRepository neo4jTermRepository) {
        this.neo4jTermRepository = neo4jTermRepository;
    }

    @Override
    public Term create(Term term) {

        return null;
    }
}
