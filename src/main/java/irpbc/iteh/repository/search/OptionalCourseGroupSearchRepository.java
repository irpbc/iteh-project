package irpbc.iteh.repository.search;

import irpbc.iteh.domain.OptionalCourseGroup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the OptionalCourseGroup entity.
 */
public interface OptionalCourseGroupSearchRepository extends ElasticsearchRepository<OptionalCourseGroup, Long> {
}
