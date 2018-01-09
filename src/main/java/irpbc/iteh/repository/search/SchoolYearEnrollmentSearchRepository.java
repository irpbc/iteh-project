package irpbc.iteh.repository.search;

import irpbc.iteh.domain.SchoolYearEnrollment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SchoolYearEnrollment entity.
 */
public interface SchoolYearEnrollmentSearchRepository extends ElasticsearchRepository<SchoolYearEnrollment, Long> {
}
