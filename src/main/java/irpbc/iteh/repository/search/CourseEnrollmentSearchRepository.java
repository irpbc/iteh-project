package irpbc.iteh.repository.search;

import irpbc.iteh.domain.CourseEnrollment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CourseEnrollment entity.
 */
public interface CourseEnrollmentSearchRepository extends ElasticsearchRepository<CourseEnrollment, Long> {
}
