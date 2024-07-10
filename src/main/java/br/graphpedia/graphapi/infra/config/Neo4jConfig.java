package br.graphpedia.graphapi.infra.config;

import org.neo4j.cypherdsl.core.renderer.Configuration;
import org.neo4j.cypherdsl.core.renderer.Dialect;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.config.EnableNeo4jAuditing;

@org.springframework.context.annotation.Configuration
@EnableNeo4jAuditing
public class Neo4jConfig {
    @Bean
    Configuration cypherDslConfiguration() {
        return Configuration.newConfig()
                .withDialect(Dialect.NEO4J_5).build();
    }

}
