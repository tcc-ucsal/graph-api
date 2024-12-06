package br.graphpedia.graphapi.infra.config;

import br.graphpedia.graphapi.core.exceptions.SetupException;
import br.graphpedia.graphapi.infra.database.elasticsearch.entity.TermContextEntity;
import br.graphpedia.graphapi.infra.database.elasticsearch.repository.ElasticsearchContextTermRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.elasticsearch.client.ClientConfiguration;


@org.springframework.context.annotation.Configuration
@EnableElasticsearchRepositories(basePackageClasses = {ElasticsearchContextTermRepository.class})
public class ElasticsearchConfig implements ApplicationRunner {

    @Value("${spring.elasticsearch.uris}")
    private String elasticsearchUri;

    @Value("${spring.elasticsearch.username}")
    private String elasticsearchUsername;

    @Value("${spring.elasticsearch.password}")
    private String elasticsearchPassword;

    private final ElasticsearchOperations elasticsearchOperations;

    @Autowired
    public ElasticsearchConfig(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Bean
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(elasticsearchUri)
                .withBasicAuth(elasticsearchUsername, elasticsearchPassword)
                .build();


    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String indexName = "term_context";
        Class<?> entityClass = TermContextEntity.class;

        try{
            IndexCoordinates indexCoordinates = IndexCoordinates.of(indexName);
            boolean indexExists = elasticsearchOperations.indexOps(indexCoordinates).exists();

            if (!indexExists) {
                boolean created = elasticsearchOperations.indexOps(indexCoordinates).create();
                if (created) {
                    elasticsearchOperations.indexOps(indexCoordinates).putMapping(
                            elasticsearchOperations.indexOps(entityClass).createMapping()
                    );
                } else {
                    throw new SetupException("Elastic Search index error: " + indexName);
                }
            }
        }catch (Exception e){
            throw new SetupException(e);
        }
    }
}
