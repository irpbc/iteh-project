package irpbc.iteh.repository.search;

import irpbc.iteh.domain.Commitment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Commitment entity.
 */
public interface CommitmentSearchRepository extends ElasticsearchRepository<Commitment, Long> {
}
