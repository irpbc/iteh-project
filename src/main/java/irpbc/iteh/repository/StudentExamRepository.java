package irpbc.iteh.repository;

import irpbc.iteh.domain.StudentExam;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the StudentExam entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentExamRepository extends JpaRepository<StudentExam, Long> {

}
