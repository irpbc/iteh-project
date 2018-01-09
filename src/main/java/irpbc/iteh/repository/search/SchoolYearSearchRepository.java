package irpbc.iteh.repository.search;

import irpbc.iteh.domain.SchoolYear;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SchoolYear entity.
 */
public interface SchoolYearSearchRepository extends ElasticsearchRepository<SchoolYear, Long> {
}
