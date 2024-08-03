package br.graphpedia.graphapi.infra.database.elasticsearch.repository;

import br.graphpedia.graphapi.infra.database.elasticsearch.entity.TermContextEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface ElasticsearchContextTermRepository extends ElasticsearchRepository<TermContextEntity, String> {

    Optional<TermContextEntity> findOneByTitleOrSynonyms(String searchTerm);

}
