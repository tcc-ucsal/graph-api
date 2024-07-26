package br.graphpedia.graphapi.infra.database.elasticsearch.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ContextTermRepositoryImpl {
    private ElasticsearchContextTermRepository elasticsearchContextTermRepository;

    @Autowired
    public ContextTermRepositoryImpl(ElasticsearchContextTermRepository elasticsearchContextTermRepository) {
        this.elasticsearchContextTermRepository = elasticsearchContextTermRepository;
    }
}
