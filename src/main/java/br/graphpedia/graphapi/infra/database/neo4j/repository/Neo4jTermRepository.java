package br.graphpedia.graphapi.infra.database.neo4j.repository;

import br.graphpedia.graphapi.infra.database.neo4j.entity.ConnectionWithEntity;
import br.graphpedia.graphapi.infra.database.neo4j.entity.TermEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

//TODO: UNWIND: https://neo4j.com/docs/cypher-manual/current/clauses/unwind/?utm_source=Google&utm_medium=PaidSearch&utm_campaign=Evergreen&utm_content=AMS-Search-SEMCE-DSA-None-SEM-SEM-NonABM&utm_term=&utm_adgroup=DSA&gad_source=1&gclid=CjwKCAjwg8qzBhAoEiwAWagLrFxEnrhOPBvJRVqyqp--xGsITSGlP9Ic4oZkG7xSpT1rgRHSf5QXThoCsHEQAvD_BwE
//TODO: MARGE: https://neo4j.com/docs/cypher-manual/current/clauses/merge/#query-merge-on-create-on-match
//TODO: UPDATE: https://neo4j.com/docs/getting-started/cypher-intro/updating/
//todo: https://docs.spring.io/spring-data/neo4j/docs/current-SNAPSHOT/reference/html/#faq.custom-queries-and-custom-mappings
public interface Neo4jTermRepository extends Neo4jRepository<TermEntity, String> {

}
