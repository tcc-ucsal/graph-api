package br.graphpedia.graphapi.infra.config;

import br.graphpedia.graphapi.infra.database.elasticsearch.repository.ElasticsearchContextTermRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@org.springframework.context.annotation.Configuration
@EnableElasticsearchRepositories(basePackageClasses = {ElasticsearchContextTermRepository.class})
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    @Value("${spring.elasticsearch.uris}")
    private String elasticsearchUri;

    @Value("${spring.elasticsearch.username}")
    private String username;

    @Value("${spring.elasticsearch.password}")
    private String password;
    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(elasticsearchUri)
                .build();

    }
}
