package br.graphpedia.graphapi.infra.database.neo4j.repository;

import br.graphpedia.graphapi.infra.database.neo4j.entity.TermEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.lang.NonNull;

public interface Neo4jTermRepository extends Neo4jRepository<TermEntity, String> {
//    long deleteByTitleIgnoreCase(@NonNull String title);
//
//    @Query("""
//        MATCH (n { title: $termTitle })
//        OPTIONAL MATCH (other)-[r:CONNECTION_WITH]->(n)
//        RETURN COUNT(r) as incomingConnections
//    """)
//    Long countIncomingConnections(String termTitle);
}
