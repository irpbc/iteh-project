package irpbc.iteh.repository.search;

import irpbc.iteh.domain.StudentCommitment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the StudentCommitment entity.
 */
public interface StudentCommitmentSearchRepository extends ElasticsearchRepository<StudentCommitment, Long> {
}
