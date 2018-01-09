package irpbc.iteh.repository.search;

import irpbc.iteh.domain.Exam;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Exam entity.
 */
public interface ExamSearchRepository extends ElasticsearchRepository<Exam, Long> {
}
