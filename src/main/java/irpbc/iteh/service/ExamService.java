package irpbc.iteh.service;

import irpbc.iteh.domain.Exam;
import irpbc.iteh.repository.ExamRepository;
import irpbc.iteh.repository.search.ExamSearchRepository;
import irpbc.iteh.service.dto.ExamDTO;
import irpbc.iteh.service.mapper.ExamMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Exam.
 */
@Service
@Transactional
public class ExamService {

    private final Logger log = LoggerFactory.getLogger(ExamService.class);

    private final ExamRepository examRepository;

    private final ExamMapper examMapper;

    private final ExamSearchRepository examSearchRepository;

    public ExamService(ExamRepository examRepository, ExamMapper examMapper, ExamSearchRepository examSearchRepository) {
        this.examRepository = examRepository;
        this.examMapper = examMapper;
        this.examSearchRepository = examSearchRepository;
    }

    /**
     * Save a exam.
     *
     * @param examDTO the entity to save
     * @return the persisted entity
     */
    public ExamDTO save(ExamDTO examDTO) {
        log.debug("Request to save Exam : {}", examDTO);
        Exam exam = examMapper.toEntity(examDTO);
        exam = examRepository.save(exam);
        ExamDTO result = examMapper.toDto(exam);
        examSearchRepository.save(exam);
        return result;
    }

    /**
     * Get all the exams.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ExamDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Exams");
        return examRepository.findAll(pageable)
            .map(examMapper::toDto);
    }

    /**
     * Get one exam by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ExamDTO findOne(Long id) {
        log.debug("Request to get Exam : {}", id);
        Exam exam = examRepository.findOne(id);
        return examMapper.toDto(exam);
    }

    /**
     * Delete the exam by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Exam : {}", id);
        examRepository.delete(id);
        examSearchRepository.delete(id);
    }

    /**
     * Search for the exam corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ExamDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Exams for query {}", query);
        Page<Exam> result = examSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(examMapper::toDto);
    }
}
