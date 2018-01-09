package irpbc.iteh.repository.search;

import irpbc.iteh.domain.Semester;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Semester entity.
 */
public interface SemesterSearchRepository extends ElasticsearchRepository<Semester, Long> {
}
