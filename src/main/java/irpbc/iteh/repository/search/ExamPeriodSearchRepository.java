package irpbc.iteh.repository.search;

import irpbc.iteh.domain.ExamPeriod;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ExamPeriod entity.
 */
public interface ExamPeriodSearchRepository extends ElasticsearchRepository<ExamPeriod, Long> {
}
