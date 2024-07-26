package br.graphpedia.graphapi.infra.database.elasticsearch.repository;

import br.graphpedia.graphapi.infra.database.elasticsearch.entity.TermContextEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticsearchContextTermRepository extends ElasticsearchRepository<TermContextEntity, String> {
}
