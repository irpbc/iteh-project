package irpbc.iteh.service;

import irpbc.iteh.domain.CourseEnrollment;
import irpbc.iteh.repository.CourseEnrollmentRepository;
import irpbc.iteh.repository.search.CourseEnrollmentSearchRepository;
import irpbc.iteh.service.dto.CourseEnrollmentDTO;
import irpbc.iteh.service.mapper.CourseEnrollmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing CourseEnrollment.
 */
@Service
@Transactional
public class CourseEnrollmentService {

    private final Logger log = LoggerFactory.getLogger(CourseEnrollmentService.class);

    private final CourseEnrollmentRepository courseEnrollmentRepository;

    private final CourseEnrollmentMapper courseEnrollmentMapper;

    private final CourseEnrollmentSearchRepository courseEnrollmentSearchRepository;

    public CourseEnrollmentService(CourseEnrollmentRepository courseEnrollmentRepository, CourseEnrollmentMapper courseEnrollmentMapper, CourseEnrollmentSearchRepository courseEnrollmentSearchRepository) {
        this.courseEnrollmentRepository = courseEnrollmentRepository;
        this.courseEnrollmentMapper = courseEnrollmentMapper;
        this.courseEnrollmentSearchRepository = courseEnrollmentSearchRepository;
    }

    /**
     * Save a courseEnrollment.
     *
     * @param courseEnrollmentDTO the entity to save
     * @return the persisted entity
     */
    public CourseEnrollmentDTO save(CourseEnrollmentDTO courseEnrollmentDTO) {
        log.debug("Request to save CourseEnrollment : {}", courseEnrollmentDTO);
        CourseEnrollment courseEnrollment = courseEnrollmentMapper.toEntity(courseEnrollmentDTO);
        courseEnrollment = courseEnrollmentRepository.save(courseEnrollment);
        CourseEnrollmentDTO result = courseEnrollmentMapper.toDto(courseEnrollment);
        courseEnrollmentSearchRepository.save(courseEnrollment);
        return result;
    }

    /**
     * Get all the courseEnrollments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CourseEnrollmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CourseEnrollments");
        return courseEnrollmentRepository.findAll(pageable)
            .map(courseEnrollmentMapper::toDto);
    }

    /**
     * Get one courseEnrollment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public CourseEnrollmentDTO findOne(Long id) {
        log.debug("Request to get CourseEnrollment : {}", id);
        CourseEnrollment courseEnrollment = courseEnrollmentRepository.findOne(id);
        return courseEnrollmentMapper.toDto(courseEnrollment);
    }

    /**
     * Delete the courseEnrollment by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CourseEnrollment : {}", id);
        courseEnrollmentRepository.delete(id);
        courseEnrollmentSearchRepository.delete(id);
    }

    /**
     * Search for the courseEnrollment corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CourseEnrollmentDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CourseEnrollments for query {}", query);
        Page<CourseEnrollment> result = courseEnrollmentSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(courseEnrollmentMapper::toDto);
    }
}
