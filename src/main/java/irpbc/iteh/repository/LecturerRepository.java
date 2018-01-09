package irpbc.iteh.repository;

import irpbc.iteh.domain.Lecturer;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Lecturer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Long> {

}
