package br.graphpedia.graphapi.infra.database.neo4j.repository;

import br.graphpedia.graphapi.infra.database.neo4j.entity.TermEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.UUID;

public interface Neo4jTermRepository extends Neo4jRepository<TermEntity, String> {
}
