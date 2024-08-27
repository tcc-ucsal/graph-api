package br.graphpedia.graphapi.infra.database.elasticsearch.repository;

import br.graphpedia.graphapi.core.entity.TermContext;
import br.graphpedia.graphapi.core.exceptions.PersistenceException;
import br.graphpedia.graphapi.core.persistence.IContextTermRepository;
import br.graphpedia.graphapi.infra.database.elasticsearch.entity.TermContextEntity;
import br.graphpedia.graphapi.infra.database.elasticsearch.mapper.TermContextElasticMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ContextTermRepositoryImpl implements IContextTermRepository {
    private final ElasticsearchContextTermRepository elasticsearchContextTermRepository;

    @Autowired
    public ContextTermRepositoryImpl(ElasticsearchContextTermRepository elasticsearchContextTermRepository, ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchContextTermRepository = elasticsearchContextTermRepository;
    }

    @Override
    public TermContext save(TermContext context) {
        TermContextEntity entity = TermContextElasticMapper.INSTANCE.toEntity(context);
        return TermContextElasticMapper.INSTANCE.toTermContextCore(elasticsearchContextTermRepository.save(entity));
    }

    @Override
    public Optional<TermContext> findByTitleOrSynonyms(String term) {
        try{
            Optional<TermContextEntity> context = elasticsearchContextTermRepository.findByTitleIgnoreCaseOrSynonymsContainsIgnoreCase(term, term);
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
