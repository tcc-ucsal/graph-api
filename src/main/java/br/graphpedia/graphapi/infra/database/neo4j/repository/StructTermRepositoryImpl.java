package br.graphpedia.graphapi.infra.database.neo4j.repository;

import br.graphpedia.graphapi.app.dto.ConnectionWithDTO;
import br.graphpedia.graphapi.core.entity.ConnectionWith;
import br.graphpedia.graphapi.app.dto.ConnectionWithCountDTO;
import br.graphpedia.graphapi.core.entity.Term;
import br.graphpedia.graphapi.core.exceptions.PersistenceException;
import br.graphpedia.graphapi.core.persistence.IStructTermRepository;
import br.graphpedia.graphapi.infra.database.neo4j.entity.TermEntity;
import br.graphpedia.graphapi.infra.database.neo4j.mapper.TermNeo4jMapper;
import br.graphpedia.graphapi.infra.utils.Neo4jObjectConverter;
import org.neo4j.driver.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

@Repository
public class StructTermRepositoryImpl implements IStructTermRepository {

    private final Neo4jTermRepository neo4jTermRepository;
    private final Neo4jClient neo4jClient;

    @Autowired
    public StructTermRepositoryImpl(Neo4jTermRepository neo4jTermRepository, Neo4jClient neo4jClient) {
        this.neo4jTermRepository = neo4jTermRepository;
        this.neo4jClient = neo4jClient;
    }

    @Override
    public Term create(Term term) {
        validateTerm(term);
        List<TermEntity> cratedEntity;
        try {
            String cypher = "MERGE (termC:Term {title: $term.title}) " +
                    "ON CREATE SET " +
                    "              termC.title = $term.title, " +
                    "              termC.createdDate = timestamp() " +
                    "ON MATCH SET " +
                    "              termC.source = $term.source, " +
                    "              termC.updatedDate = timestamp() " +
                    "WITH termC " +
                    "UNWIND $term.connectionWiths AS connection " +
                    "MERGE (term:Term {title: connection.targetTerm.title}) " +
                    "MERGE (termC)-[c:CONNECTION_WITH {relevance_level: connection.relevanceLevel}]->(term) " +
                    "   ON CREATE SET c.relevance_level = connection.relevanceLevel " +
                    "   ON MATCH SET c.relevance_level = (CASE WHEN c.relevance_level >= connection.relevanceLevel THEN connection.relevanceLevel ELSE c.relevance_level END ) " +
                    "RETURN termC.id as id, termC.title as title, " +
                    "termC.createdDate as createdDate, termC.updatedDate as updatedDate";

            cratedEntity = neo4jClient.query(cypher)
                    .bind(Neo4jObjectConverter.convertToMap(term)).to("term")
                    .fetchAs(TermEntity.class)
                    .mappedBy((typeSystem, register) ->
                            new TermEntity(register.get("id").asString(), register.get("title").asString(),
                                    getLocalDateTimeByRecord(register, "createdDate"),
                                    getLocalDateTimeByRecord(register,"updatedDate")))
                    .all().stream().toList();

            return TermNeo4jMapper.INSTANCE.toTermCore(cratedEntity.stream().findFirst()
                    .orElseThrow(() -> new PersistenceException("Access result error")));

        } catch (Exception exception){

            throw new PersistenceException(exception.getMessage(), exception.getCause());
        }

    }

    @Override
    public List<ConnectionWithCountDTO> getConnectionsWithLevelOneCount(String title) {
        String query = """
            MATCH (t:Term)-[r:CONNECTION_WITH]->(target:Term)
            WHERE t.title = $termTitle
            OPTIONAL MATCH (target)-[c:CONNECTION_WITH {relevance_level: 1}]->(other)
            WITH target, r, COUNT(other) as connectionCount
            RETURN target, r, connectionCount
            """;

        return neo4jClient.query(query)
                .bind(title).to("termTitle")
                .fetchAs(ConnectionWithCountDTO.class)
                .mappedBy((typeSystem, register) -> {
                    Term targetTerm = new Term();
                    targetTerm.setId(register.get("target").get("id").asString());
                    targetTerm.setTitle(register.get("target").get("title").asString());

                    Value rValue = register.get("r");
                    ConnectionWithDTO connectionWithDTO = new ConnectionWithDTO(
                            rValue.get("id").asString(),
                            title,
                            rValue.get("relevance_level").asInt(),
                            targetTerm
                    );

                    int connectionCount = register.get("connectionCount").asInt();

                    return new ConnectionWithCountDTO(connectionWithDTO, connectionCount);
                })
                .all().stream().toList();

    }

    private static void validateTerm(Term term) {
        if (term.getTitle() == null || term.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }

        for (ConnectionWith connection : term.getConnectionWiths()) {
            if (connection.getTargetTerm().getTitle() == null || connection.getTargetTerm().getTitle().isEmpty()) {
                throw new IllegalArgumentException("Target term title cannot be null or empty");
            }
        }
    }

    private static LocalDateTime getLocalDateTimeByRecord(org.neo4j.driver.Record register, String fieldName) {

        return register.get(fieldName).isNull() ? null :
                LocalDateTime.ofInstant(Instant.ofEpochSecond(register.get(fieldName).asLong()), ZoneId.of("America/Sao_Paulo"));
    }

    public void deleteAll(){
        neo4jTermRepository.deleteAll();
    }

    @Override
    public void deleteByTitleIfNotIncomingConnections(String title) {
        Long incomingConnections = neo4jTermRepository.countIncomingConnections(title);

        if(incomingConnections == 0L){
            neo4jTermRepository.deleteByTitleIgnoreCase(title);
        }
    }

    @Override
    public List<ConnectionWith> getConnectionByLevel(String[] titles, int level, int limit) {

        String query = """
            UNWIND $termTitles AS termTitle
            MATCH (t:Term {title: termTitle})-[r:CONNECTION_WITH {relevance_level: $level}]->(target:Term)
            RETURN target, r, t.title as mainTitle
            LIMIT $limit
            """;

        return neo4jClient.query(query)
                .bind(Neo4jObjectConverter.convertToMap(level)).to("$level")
                .bind(Neo4jObjectConverter.convertToMap(titles)).to("$termTitles")
                .bind(Neo4jObjectConverter.convertToMap(limit)).to("$limit")
                .fetchAs(ConnectionWith.class)
                .mappedBy((typeSystem, register) -> {

                    Term targetTerm = new Term();
                    targetTerm.setId(register.get("target").get("id").asString());
                    targetTerm.setTitle(register.get("target").get("title").asString());

                    ConnectionWith connectionWithEntity = new ConnectionWith();
                    connectionWithEntity.setId(register.get("r").get("id").asString());
                    connectionWithEntity.setRelevanceLevel(register.get("r").get("relevance_level").asInt());
                    connectionWithEntity.setMainTitle(register.get("mainTitle").asString());
                    connectionWithEntity.setTargetTerm(targetTerm);
                    return connectionWithEntity;
                })
                .all().stream().toList();

    }

}
