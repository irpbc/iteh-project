package irpbc.iteh.repository.search;

import irpbc.iteh.domain.StudentExam;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the StudentExam entity.
 */
public interface StudentExamSearchRepository extends ElasticsearchRepository<StudentExam, Long> {
}
