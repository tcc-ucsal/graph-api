package br.graphpedia.graphapi.infra.config;

import br.graphpedia.graphapi.infra.database.neo4j.repository.Neo4jTermRepository;
import org.neo4j.cypherdsl.core.renderer.Configuration;
import org.neo4j.cypherdsl.core.renderer.Dialect;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.config.EnableNeo4jAuditing;
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.PlatformTransactionManager;

@org.springframework.context.annotation.Configuration
@EnableNeo4jAuditing
@EnableNeo4jRepositories(basePackageClasses = {Neo4jTermRepository.class})
public class Neo4jConfig {
    @Bean
    Configuration cypherDslConfiguration() {
        return Configuration.newConfig()
                .withDialect(Dialect.NEO4J_5).build();
    }
}
