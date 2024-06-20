package br.graphpedia.graphapi.infra.database.neo4j.repository;

import br.graphpedia.graphapi.core.entity.Term;
import br.graphpedia.graphapi.core.persistence.ITermRepository;
import br.graphpedia.graphapi.infra.database.neo4j.entity.TermEntity;
import br.graphpedia.graphapi.infra.database.neo4j.mapper.TermNeo4jMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TermRepositoryImpl implements ITermRepository {
    private final Neo4jTermRepository neo4jTermRepository;

    @Autowired
    public TermRepositoryImpl(Neo4jTermRepository neo4jTermRepository) {
        this.neo4jTermRepository = neo4jTermRepository;
    }

    @Override
    public Term create(Term term) {
        TermEntity cratedEntity = neo4jTermRepository.save(TermNeo4jMapper.INSTANCE.toTermEntity(term));
        return TermNeo4jMapper.INSTANCE.toTermCore(cratedEntity);
    }

    @Override
    public List<Term> findAll() {
        List<TermEntity> list = neo4jTermRepository.findAll();
        return TermNeo4jMapper.INSTANCE.toTermCore(list);
    }

    public void deleteAll(){
        neo4jTermRepository.deleteAll();
    }

}
