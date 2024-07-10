package br.graphpedia.graphapi.infra.database.neo4j.repository;

import br.graphpedia.graphapi.core.entity.ConnectionWith;
import br.graphpedia.graphapi.core.entity.Term;
import br.graphpedia.graphapi.core.persistence.ITermRepository;
import br.graphpedia.graphapi.infra.database.neo4j.entity.ConnectionWithEntity;
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
import java.util.Objects;
import java.util.Optional;

//TODO: Fazer/implementar função de geração de UUID
@Repository
public class TermRepositoryImpl implements ITermRepository {

    private final Neo4jTermRepository neo4jTermRepository;
    private final Neo4jClient neo4jClient;

    @Autowired
    public TermRepositoryImpl(Neo4jTermRepository neo4jTermRepository, Neo4jClient neo4jClient) {
        this.neo4jTermRepository = neo4jTermRepository;
        this.neo4jClient = neo4jClient;
    }

    @Override
    public Term create(Term term) {
        if (term.getTitle() == null || term.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }

        for (ConnectionWith connection : term.getConnectionWiths()) {
            if (connection.getTargetTerm().getTitle() == null || connection.getTargetTerm().getTitle().isEmpty()) {
                throw new IllegalArgumentException("Target term title cannot be null or empty");
            }
        }

        //todo: perguntar: sempre vamos criar um no novo? quando ja tiver uma manga, eu crio um novo no ou aproveito ele?
        String cypher = "MERGE (termC:Term {title: $term.title}) " +
                "ON CREATE SET termC.title = $term.title, " +
                "              termC.description = $term.description, " +
                "              termC.source = $term.source " +
                "              termC.createdDate = timestamp() " +
                "ON MATCH SET termC.description = $term.description, " +
                "              termC.source = $term.source " +
                "              termC.updatedDate = timestamp()" +
                "WITH termC " +
                "UNWIND $term.connectionWiths AS connection " +
                "MERGE (term:Term {title: connection.targetTerm.title}) " +
                "MERGE (termC)-[c:CONNECTION_WITH {relevance_level: connection.relevanceLevel}]->(term) " +
                "   ON CREATE SET c.relevance_level = connection.relevanceLevel " +
                "   ON MATCH SET c.relevance_level = (CASE WHEN c.relevance_level >= connection.relevanceLevel THEN c.relevance_level ELSE connection.relevanceLevel END ) " +
                "RETURN termC.id as id, termC.title as title, termC.description as description, termC.source as source, " +
                "termC.createdDate as createdDate, termC.updatedDate as updatedDate";

        List<TermEntity> cratedEntity = neo4jClient.query(cypher)
                .bind(Neo4jObjectConverter.convertToMap(term)).to("term")
                .fetchAs(TermEntity.class)
                .mappedBy((typeSystem, register) ->
                        new TermEntity(register.get("id").asString(), register.get("title").asString(), register.get("description").asString(),
                                register.get("source").asString(),
                                getLocalDateTimeByRecord(register, "createdDate"),
                                getLocalDateTimeByRecord(register,"updatedDate")))
                .all().stream().toList();
        return TermNeo4jMapper.INSTANCE.toTermCore(cratedEntity).get(0);
    }

    private static LocalDateTime getLocalDateTimeByRecord(org.neo4j.driver.Record register, String fieldName) {

        return register.get(fieldName).isNull() ? null :
                LocalDateTime.ofInstant(Instant.ofEpochSecond(register.get(fieldName).asLong()), ZoneId.of("America/Sao_Paulo"));
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
