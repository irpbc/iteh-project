package irpbc.iteh.repository;

import irpbc.iteh.domain.ExamPeriod;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ExamPeriod entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExamPeriodRepository extends JpaRepository<ExamPeriod, Long> {

}
