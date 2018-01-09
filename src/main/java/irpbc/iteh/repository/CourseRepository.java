package irpbc.iteh.repository;

import irpbc.iteh.domain.Course;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Course entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("select distinct course from Course course left join fetch course.lecturers")
    List<Course> findAllWithEagerRelationships();

    @Query("select course from Course course left join fetch course.lecturers where course.id =:id")
    Course findOneWithEagerRelationships(@Param("id") Long id);

}
