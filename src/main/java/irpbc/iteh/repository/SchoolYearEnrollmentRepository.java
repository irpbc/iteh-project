package irpbc.iteh.repository;

import irpbc.iteh.domain.SchoolYearEnrollment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SchoolYearEnrollment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SchoolYearEnrollmentRepository extends JpaRepository<SchoolYearEnrollment, Long> {

}
