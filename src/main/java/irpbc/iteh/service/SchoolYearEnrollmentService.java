package irpbc.iteh.service;

import irpbc.iteh.domain.SchoolYearEnrollment;
import irpbc.iteh.repository.SchoolYearEnrollmentRepository;
import irpbc.iteh.repository.search.SchoolYearEnrollmentSearchRepository;
import irpbc.iteh.service.dto.SchoolYearEnrollmentDTO;
import irpbc.iteh.service.mapper.SchoolYearEnrollmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing SchoolYearEnrollment.
 */
@Service
@Transactional
public class SchoolYearEnrollmentService {

    private final Logger log = LoggerFactory.getLogger(SchoolYearEnrollmentService.class);

    private final SchoolYearEnrollmentRepository schoolYearEnrollmentRepository;

    private final SchoolYearEnrollmentMapper schoolYearEnrollmentMapper;

    private final SchoolYearEnrollmentSearchRepository schoolYearEnrollmentSearchRepository;

    public SchoolYearEnrollmentService(SchoolYearEnrollmentRepository schoolYearEnrollmentRepository, SchoolYearEnrollmentMapper schoolYearEnrollmentMapper, SchoolYearEnrollmentSearchRepository schoolYearEnrollmentSearchRepository) {
        this.schoolYearEnrollmentRepository = schoolYearEnrollmentRepository;
        this.schoolYearEnrollmentMapper = schoolYearEnrollmentMapper;
        this.schoolYearEnrollmentSearchRepository = schoolYearEnrollmentSearchRepository;
    }

    /**
     * Save a schoolYearEnrollment.
     *
     * @param schoolYearEnrollmentDTO the entity to save
     * @return the persisted entity
     */
    public SchoolYearEnrollmentDTO save(SchoolYearEnrollmentDTO schoolYearEnrollmentDTO) {
        log.debug("Request to save SchoolYearEnrollment : {}", schoolYearEnrollmentDTO);
        SchoolYearEnrollment schoolYearEnrollment = schoolYearEnrollmentMapper.toEntity(schoolYearEnrollmentDTO);
        schoolYearEnrollment = schoolYearEnrollmentRepository.save(schoolYearEnrollment);
        SchoolYearEnrollmentDTO result = schoolYearEnrollmentMapper.toDto(schoolYearEnrollment);
        schoolYearEnrollmentSearchRepository.save(schoolYearEnrollment);
        return result;
    }

    /**
     * Get all the schoolYearEnrollments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SchoolYearEnrollmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SchoolYearEnrollments");
        return schoolYearEnrollmentRepository.findAll(pageable)
            .map(schoolYearEnrollmentMapper::toDto);
    }

    /**
     * Get one schoolYearEnrollment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public SchoolYearEnrollmentDTO findOne(Long id) {
        log.debug("Request to get SchoolYearEnrollment : {}", id);
        SchoolYearEnrollment schoolYearEnrollment = schoolYearEnrollmentRepository.findOne(id);
        return schoolYearEnrollmentMapper.toDto(schoolYearEnrollment);
    }

    /**
     * Delete the schoolYearEnrollment by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SchoolYearEnrollment : {}", id);
        schoolYearEnrollmentRepository.delete(id);
        schoolYearEnrollmentSearchRepository.delete(id);
    }

    /**
     * Search for the schoolYearEnrollment corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SchoolYearEnrollmentDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SchoolYearEnrollments for query {}", query);
        Page<SchoolYearEnrollment> result = schoolYearEnrollmentSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(schoolYearEnrollmentMapper::toDto);
    }
}
