package irpbc.iteh.repository;

import irpbc.iteh.domain.StudentCommitment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the StudentCommitment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentCommitmentRepository extends JpaRepository<StudentCommitment, Long> {

}
