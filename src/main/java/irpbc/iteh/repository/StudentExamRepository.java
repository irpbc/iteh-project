package irpbc.iteh.repository;

import irpbc.iteh.domain.Exam;
import irpbc.iteh.domain.StudentExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the StudentExam entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentExamRepository extends JpaRepository<StudentExam, Long> {

    @Query("select e from Exam e \n" +
        "join CourseEnrollment ce on (ce.course = e.course)" +
        "join ce.yearEnrollment ye \n" +
        "where ye.student.id = :studentId and e.period.id = :periodId \n" +
        "and e.id not in (select e.id \n" +
        "                 from StudentExam se join se.exam e \n" +
        "                 where se.student.id = :studentId and e.period.id = :periodId)")
    List<Exam> findNextExamsForStudent(@Param("studentId") Long studentId, @Param("periodId") Long periodId);

    @Query("select e \n" +
        "from StudentExam se join se.exam e \n" +
        "where se.student.id = ?1 and e.period.id = ?2")
    List<Exam> findAppliedExams(Long studentId, Long periodId);

    void deleteByExam_IdAndStudent_Id(long examId, Long studentId);
}
