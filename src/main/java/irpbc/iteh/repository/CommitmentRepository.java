package irpbc.iteh.repository;

import irpbc.iteh.domain.Commitment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Commitment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommitmentRepository extends JpaRepository<Commitment, Long> {

}
