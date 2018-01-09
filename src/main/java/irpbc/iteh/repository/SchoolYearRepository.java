package irpbc.iteh.repository;

import irpbc.iteh.domain.SchoolYear;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SchoolYear entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SchoolYearRepository extends JpaRepository<SchoolYear, Long> {

}
