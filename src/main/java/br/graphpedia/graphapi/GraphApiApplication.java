package br.graphpedia.graphapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@EnableTransactionManagement
public class GraphApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraphApiApplication.class, args);
	}

}
