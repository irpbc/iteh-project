package irpbc.iteh.repository;

import irpbc.iteh.domain.OptionalCourseGroup;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the OptionalCourseGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OptionalCourseGroupRepository extends JpaRepository<OptionalCourseGroup, Long> {

}
