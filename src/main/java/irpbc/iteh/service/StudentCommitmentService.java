package irpbc.iteh.service;

import irpbc.iteh.domain.StudentCommitment;
import irpbc.iteh.repository.StudentCommitmentRepository;
import irpbc.iteh.repository.search.StudentCommitmentSearchRepository;
import irpbc.iteh.service.dto.StudentCommitmentDTO;
import irpbc.iteh.service.mapper.StudentCommitmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing StudentCommitment.
 */
@Service
@Transactional
public class StudentCommitmentService {

    private final Logger log = LoggerFactory.getLogger(StudentCommitmentService.class);

    private final StudentCommitmentRepository studentCommitmentRepository;

    private final StudentCommitmentMapper studentCommitmentMapper;

    private final StudentCommitmentSearchRepository studentCommitmentSearchRepository;

    public StudentCommitmentService(StudentCommitmentRepository studentCommitmentRepository, StudentCommitmentMapper studentCommitmentMapper, StudentCommitmentSearchRepository studentCommitmentSearchRepository) {
        this.studentCommitmentRepository = studentCommitmentRepository;
        this.studentCommitmentMapper = studentCommitmentMapper;
        this.studentCommitmentSearchRepository = studentCommitmentSearchRepository;
    }

    /**
     * Save a studentCommitment.
     *
     * @param studentCommitmentDTO the entity to save
     * @return the persisted entity
     */
    public StudentCommitmentDTO save(StudentCommitmentDTO studentCommitmentDTO) {
        log.debug("Request to save StudentCommitment : {}", studentCommitmentDTO);
        StudentCommitment studentCommitment = studentCommitmentMapper.toEntity(studentCommitmentDTO);
        studentCommitment = studentCommitmentRepository.save(studentCommitment);
        StudentCommitmentDTO result = studentCommitmentMapper.toDto(studentCommitment);
        studentCommitmentSearchRepository.save(studentCommitment);
        return result;
    }

    /**
     * Get all the studentCommitments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<StudentCommitmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StudentCommitments");
        return studentCommitmentRepository.findAll(pageable)
            .map(studentCommitmentMapper::toDto);
    }

    /**
     * Get one studentCommitment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public StudentCommitmentDTO findOne(Long id) {
        log.debug("Request to get StudentCommitment : {}", id);
        StudentCommitment studentCommitment = studentCommitmentRepository.findOne(id);
        return studentCommitmentMapper.toDto(studentCommitment);
    }

    /**
     * Delete the studentCommitment by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete StudentCommitment : {}", id);
        studentCommitmentRepository.delete(id);
        studentCommitmentSearchRepository.delete(id);
    }

    /**
     * Search for the studentCommitment corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<StudentCommitmentDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of StudentCommitments for query {}", query);
        Page<StudentCommitment> result = studentCommitmentSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(studentCommitmentMapper::toDto);
    }
}
