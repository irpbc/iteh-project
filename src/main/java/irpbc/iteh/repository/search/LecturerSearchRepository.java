package irpbc.iteh.repository.search;

import irpbc.iteh.domain.Lecturer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Lecturer entity.
 */
public interface LecturerSearchRepository extends ElasticsearchRepository<Lecturer, Long> {
}
