package br.graphpedia.graphapi.infra.config;

import br.graphpedia.graphapi.infra.database.elasticsearch.repository.ElasticsearchContextTermRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.elasticsearch.client.ClientConfiguration;


@org.springframework.context.annotation.Configuration
@EnableElasticsearchRepositories(basePackageClasses = {ElasticsearchContextTermRepository.class})
public class ElasticsearchConfig {

    @Value("${spring.elasticsearch.uris}")
    private String elasticsearchUri;

    @Value("${spring.elasticsearch.username}")
    private String elasticsearchUsername;

    @Value("${spring.elasticsearch.password}")
    private String elasticsearchPassword;


    @Bean
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(elasticsearchUri)
                .withBasicAuth(elasticsearchUsername, elasticsearchPassword)
                .build();

    }


}
