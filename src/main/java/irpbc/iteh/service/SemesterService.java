package irpbc.iteh.service;

import irpbc.iteh.domain.Semester;
import irpbc.iteh.repository.SemesterRepository;
import irpbc.iteh.repository.search.SemesterSearchRepository;
import irpbc.iteh.service.dto.SemesterDTO;
import irpbc.iteh.service.mapper.SemesterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Semester.
 */
@Service
@Transactional
public class SemesterService {

    private final Logger log = LoggerFactory.getLogger(SemesterService.class);

    private final SemesterRepository semesterRepository;

    private final SemesterMapper semesterMapper;

    private final SemesterSearchRepository semesterSearchRepository;

    public SemesterService(SemesterRepository semesterRepository, SemesterMapper semesterMapper, SemesterSearchRepository semesterSearchRepository) {
        this.semesterRepository = semesterRepository;
        this.semesterMapper = semesterMapper;
        this.semesterSearchRepository = semesterSearchRepository;
    }

    /**
     * Save a semester.
     *
     * @param semesterDTO the entity to save
     * @return the persisted entity
     */
    public SemesterDTO save(SemesterDTO semesterDTO) {
        log.debug("Request to save Semester : {}", semesterDTO);
        Semester semester = semesterMapper.toEntity(semesterDTO);
        semester = semesterRepository.save(semester);
        SemesterDTO result = semesterMapper.toDto(semester);
        semesterSearchRepository.save(semester);
        return result;
    }

    /**
     * Get all the semesters.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SemesterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Semesters");
        return semesterRepository.findAll(pageable)
            .map(semesterMapper::toDto);
    }

    /**
     * Get one semester by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public SemesterDTO findOne(Long id) {
        log.debug("Request to get Semester : {}", id);
        Semester semester = semesterRepository.findOne(id);
        return semesterMapper.toDto(semester);
    }

    /**
     * Delete the semester by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Semester : {}", id);
        semesterRepository.delete(id);
        semesterSearchRepository.delete(id);
    }

    /**
     * Search for the semester corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SemesterDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Semesters for query {}", query);
        Page<Semester> result = semesterSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(semesterMapper::toDto);
    }
}
