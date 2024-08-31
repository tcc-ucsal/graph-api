package br.graphpedia.graphapi.infra.database.elasticsearch.repository;

import br.graphpedia.graphapi.core.entity.TermContext;
import br.graphpedia.graphapi.core.exceptions.PersistenceException;
import br.graphpedia.graphapi.core.persistence.IContextTermRepository;
import br.graphpedia.graphapi.infra.database.elasticsearch.entity.TermContextEntity;
import br.graphpedia.graphapi.infra.database.elasticsearch.mapper.TermContextElasticMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class ContextTermRepositoryImpl implements IContextTermRepository {
    private final ElasticsearchContextTermRepository elasticsearchContextTermRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    @Autowired
    public ContextTermRepositoryImpl(ElasticsearchContextTermRepository elasticsearchContextTermRepository, ElasticsearchOperations elasticsearchOperation) {
        this.elasticsearchContextTermRepository = elasticsearchContextTermRepository;
        this.elasticsearchOperations = elasticsearchOperation;
    }

    @Override
    public TermContext save(TermContext context) {
        TermContextEntity entity = TermContextElasticMapper.INSTANCE.toEntity(context);
        entity.setCreatedDate(LocalDateTime.now());
        return TermContextElasticMapper.INSTANCE.toTermContextCore(elasticsearchContextTermRepository.save(entity));
    }

    @Override
    public Optional<TermContext> findByTitleOrSynonyms(String term) {
        try{
            Criteria criteria = new Criteria("title").is(term)
                    .or(new Criteria("synonyms").is(term));

            CriteriaQuery query = new CriteriaQuery(criteria);
            SearchHits<TermContextEntity> searchHits = elasticsearchOperations.search(query, TermContextEntity.class);

            Optional<TermContextEntity> context = searchHits.getSearchHits().stream()
                    .findFirst()
                    .map(SearchHit::getContent);

            return context.map(TermContextElasticMapper.INSTANCE::toTermContextCore);

        }catch (Exception e){
            throw new PersistenceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void deleteByTitle(String title) {
        elasticsearchContextTermRepository.deleteByTitleIgnoreCase(title);
    }
}
