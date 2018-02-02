package irpbc.iteh.repository;

import irpbc.iteh.domain.ExamPeriod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

/**
 * Spring Data JPA repository for the ExamPeriod entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExamPeriodRepository extends JpaRepository<ExamPeriod, Long> {

    @Query(value = "select p from ExamPeriod p where p.startDate > ?1 order by p.startDate desc")
    Page<ExamPeriod> findNextPeriod(LocalDate currentDate, Pageable pageable);
}
