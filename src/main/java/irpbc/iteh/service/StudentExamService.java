package irpbc.iteh.service;

import irpbc.iteh.domain.StudentExam;
import irpbc.iteh.repository.StudentExamRepository;
import irpbc.iteh.repository.search.StudentExamSearchRepository;
import irpbc.iteh.service.dto.StudentExamDTO;
import irpbc.iteh.service.mapper.StudentExamMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing StudentExam.
 */
@Service
@Transactional
public class StudentExamService {

    private final Logger log = LoggerFactory.getLogger(StudentExamService.class);

    private final StudentExamRepository studentExamRepository;

    private final StudentExamMapper studentExamMapper;

    private final StudentExamSearchRepository studentExamSearchRepository;

    public StudentExamService(StudentExamRepository studentExamRepository, StudentExamMapper studentExamMapper, StudentExamSearchRepository studentExamSearchRepository) {
        this.studentExamRepository = studentExamRepository;
        this.studentExamMapper = studentExamMapper;
        this.studentExamSearchRepository = studentExamSearchRepository;
    }

    /**
     * Save a studentExam.
     *
     * @param studentExamDTO the entity to save
     * @return the persisted entity
     */
    public StudentExamDTO save(StudentExamDTO studentExamDTO) {
        log.debug("Request to save StudentExam : {}", studentExamDTO);
        StudentExam studentExam = studentExamMapper.toEntity(studentExamDTO);
        studentExam = studentExamRepository.save(studentExam);
        StudentExamDTO result = studentExamMapper.toDto(studentExam);
        studentExamSearchRepository.save(studentExam);
        return result;
    }

    /**
     * Get all the studentExams.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<StudentExamDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StudentExams");
        return studentExamRepository.findAll(pageable)
            .map(studentExamMapper::toDto);
    }

    /**
     * Get one studentExam by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public StudentExamDTO findOne(Long id) {
        log.debug("Request to get StudentExam : {}", id);
        StudentExam studentExam = studentExamRepository.findOne(id);
        return studentExamMapper.toDto(studentExam);
    }

    /**
     * Delete the studentExam by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete StudentExam : {}", id);
        studentExamRepository.delete(id);
        studentExamSearchRepository.delete(id);
    }

    /**
     * Search for the studentExam corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<StudentExamDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of StudentExams for query {}", query);
        Page<StudentExam> result = studentExamSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(studentExamMapper::toDto);
    }
}
