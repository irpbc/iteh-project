package irpbc.iteh.service;

import io.github.jhipster.service.QueryService;
import irpbc.iteh.domain.*;
import irpbc.iteh.domain.Course_;
import irpbc.iteh.domain.OptionalCourseGroup_;
import irpbc.iteh.domain.Semester_;
import irpbc.iteh.domain.User_;
import irpbc.iteh.repository.CourseRepository;
import irpbc.iteh.repository.search.CourseSearchRepository;
import irpbc.iteh.service.dto.CourseCriteria;
import irpbc.iteh.service.dto.CourseDTO;
import irpbc.iteh.service.mapper.CourseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.metamodel.SingularAttribute;
import java.util.List;

/**
 * Service for executing complex queries for Course entities in the database.
 * The main input is a {@link CourseCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CourseDTO} or a {@link Page} of {@link CourseDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CourseQueryService extends QueryService<Course> {

    private final Logger log = LoggerFactory.getLogger(CourseQueryService.class);

    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper;

    private final CourseSearchRepository courseSearchRepository;

    public CourseQueryService(CourseRepository courseRepository, CourseMapper courseMapper, CourseSearchRepository courseSearchRepository) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.courseSearchRepository = courseSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CourseDTO} which matches the criteria from the database
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CourseDTO> findByCriteria(CourseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Course> specification = createSpecification(criteria);
        return courseMapper.toDto(courseRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CourseDTO} which matches the criteria from the database
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CourseDTO> findByCriteria(CourseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Course> specification = createSpecification(criteria);
        final Page<Course> result = courseRepository.findAll(specification, page);
        return result.map(courseMapper::toDto);
    }

    /**
     * Function to convert CourseCriteria to a {@link Specifications}
     */
    @SuppressWarnings("unchecked")
    private Specifications<Course> createSpecification(CourseCriteria criteria) {
        Specifications<Course> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Course_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Course_.name));
            }
            if (criteria.getEspbPoints() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEspbPoints(), Course_.espbPoints));
            }
            if (criteria.getYearOfStudies() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYearOfStudies(), Course_.yearOfStudies));
            }
            if (criteria.getOptional() != null) {
                specification = specification.and(buildSpecification(criteria.getOptional(), Course_.optional));
            }
            if (criteria.getSemesterId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getSemesterId(), Course_.semester, (SingularAttribute) Semester_.id));
            }
            if (criteria.getOptionalGroupId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getOptionalGroupId(), Course_.optionalGroup, (SingularAttribute) OptionalCourseGroup_.id));
            }
            if (criteria.getLecturersId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getLecturersId(), Course_.lecturers, (SingularAttribute) User_.id));
            }
        }
        return specification;
    }
}
