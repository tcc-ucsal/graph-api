package br.graphpedia.graphapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@EnableNeo4jRepositories
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class GraphApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraphApiApplication.class, args);
	}

}
