package br.graphpedia.graphapi.infra.database.elasticsearch.repository;

import br.graphpedia.graphapi.infra.database.elasticsearch.entity.TermContextEntity;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface ElasticsearchContextTermRepository extends ElasticsearchRepository<TermContextEntity, String> {
    long deleteByTitleIgnoreCase(@NonNull String title);
}
