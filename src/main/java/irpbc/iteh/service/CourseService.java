package irpbc.iteh.service;

import irpbc.iteh.domain.Course;
import irpbc.iteh.repository.CourseRepository;
import irpbc.iteh.repository.search.CourseSearchRepository;
import irpbc.iteh.security.SecurityUtils;
import irpbc.iteh.service.dto.CourseDTO;
import irpbc.iteh.service.dto.StudentCourseDTO;
import irpbc.iteh.service.mapper.CourseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Course.
 */
@Service
@Transactional
public class CourseService {

    private final Logger log = LoggerFactory.getLogger(CourseService.class);

    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper;

    private final CourseSearchRepository courseSearchRepository;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper, CourseSearchRepository courseSearchRepository) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.courseSearchRepository = courseSearchRepository;
    }

    /**
     * Save a course.
     *
     * @param courseDTO the entity to save
     * @return the persisted entity
     */
    public CourseDTO save(CourseDTO courseDTO) {
        log.debug("Request to save Course : {}", courseDTO);
        Course course = courseMapper.toEntity(courseDTO);
        course = courseRepository.save(course);
        CourseDTO result = courseMapper.toDto(course);
        courseSearchRepository.save(course);
        return result;
    }

    /**
     * Get all the courses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CourseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Courses");
        return courseRepository.findAll(pageable)
            .map(courseMapper::toDto);
    }

    /**
     * Get one course by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public CourseDTO findOne(Long id) {
        log.debug("Request to get Course : {}", id);
        Course course = courseRepository.findOneWithEagerRelationships(id);
        return courseMapper.toDto(course);
    }

    /**
     * Delete the course by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Course : {}", id);
        courseRepository.delete(id);
        courseSearchRepository.delete(id);
    }

    /**
     * Search for the course corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CourseDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Courses for query {}", query);
        Page<Course> result = courseSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(courseMapper::toDto);
    }

    public Page<StudentCourseDTO> getPassedCourses(Pageable pageable) {
        Long studentId = SecurityUtils.getCurrentUserId();
        return courseRepository.findPassedCourses(studentId, pageable);
    }

    public Page<StudentCourseDTO> getDueCourses(Pageable pageable) {
        Long studentId = SecurityUtils.getCurrentUserId();
        return courseRepository.findDueCourses(studentId, pageable);
    }
}
