package br.graphpedia.graphapi.infra.database.elasticsearch.repository;

import br.graphpedia.graphapi.core.entity.TermContext;
import br.graphpedia.graphapi.core.persistence.IContextTermRepository;
import br.graphpedia.graphapi.infra.database.elasticsearch.entity.TermContextEntity;
import br.graphpedia.graphapi.infra.database.elasticsearch.mapper.TermContextElasticMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ContextTermRepositoryImpl implements IContextTermRepository {
    private ElasticsearchContextTermRepository elasticsearchContextTermRepository;

    @Autowired
    public ContextTermRepositoryImpl(ElasticsearchContextTermRepository elasticsearchContextTermRepository) {
        this.elasticsearchContextTermRepository = elasticsearchContextTermRepository;
    }

    @Override
    public TermContext save(TermContext context) {
        return null;
    }

    @Override
    public Optional<TermContext> findByTitleOrSynonyms(String term) {
        Optional<TermContextEntity> context = elasticsearchContextTermRepository.findOneByTitleOrSynonyms(term);
        return context.map(TermContextElasticMapper.INSTANCE::toTermContextCore);
    }
}
