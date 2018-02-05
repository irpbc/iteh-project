package irpbc.iteh.repository;

import irpbc.iteh.domain.Course;
import irpbc.iteh.service.dto.StudentCourseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Course entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {
    @Query("select distinct course from Course course left join fetch course.lecturers")
    List<Course> findAllWithEagerRelationships();

    @Query("select course from Course course left join fetch course.lecturers where course.id =:id")
    Course findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select new irpbc.iteh.service.dto.StudentCourseDTO(c.name, c.espbPoints, cr.grade, c.yearOfStudies, y.name) " +
        "from CourseEnrollment cr join cr.course c join cr.yearEnrollment ye join ye.year y " +
        "where ye.student.id = ?1 and cr.completed = true " +
        "order by y.startDate desc ")
    Page<StudentCourseDTO> findPassedCourses(Long studentId, Pageable pageable);

    @Query("select new irpbc.iteh.service.dto.StudentCourseDTO(c.name, c.espbPoints, c.yearOfStudies) " +
        "from CourseEnrollment cr join cr.course c join cr.yearEnrollment ye join ye.year y " +
        "where ye.student.id = ?1 and cr.completed <> true ")
    Page<StudentCourseDTO> findDueCourses(Long studentId, Pageable pageable);
}
