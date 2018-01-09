package irpbc.iteh.service;

import irpbc.iteh.domain.ExamPeriod;
import irpbc.iteh.repository.ExamPeriodRepository;
import irpbc.iteh.repository.search.ExamPeriodSearchRepository;
import irpbc.iteh.service.dto.ExamPeriodDTO;
import irpbc.iteh.service.mapper.ExamPeriodMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ExamPeriod.
 */
@Service
@Transactional
public class ExamPeriodService {

    private final Logger log = LoggerFactory.getLogger(ExamPeriodService.class);

    private final ExamPeriodRepository examPeriodRepository;

    private final ExamPeriodMapper examPeriodMapper;

    private final ExamPeriodSearchRepository examPeriodSearchRepository;

    public ExamPeriodService(ExamPeriodRepository examPeriodRepository, ExamPeriodMapper examPeriodMapper, ExamPeriodSearchRepository examPeriodSearchRepository) {
        this.examPeriodRepository = examPeriodRepository;
        this.examPeriodMapper = examPeriodMapper;
        this.examPeriodSearchRepository = examPeriodSearchRepository;
    }

    /**
     * Save a examPeriod.
     *
     * @param examPeriodDTO the entity to save
     * @return the persisted entity
     */
    public ExamPeriodDTO save(ExamPeriodDTO examPeriodDTO) {
        log.debug("Request to save ExamPeriod : {}", examPeriodDTO);
        ExamPeriod examPeriod = examPeriodMapper.toEntity(examPeriodDTO);
        examPeriod = examPeriodRepository.save(examPeriod);
        ExamPeriodDTO result = examPeriodMapper.toDto(examPeriod);
        examPeriodSearchRepository.save(examPeriod);
        return result;
    }

    /**
     * Get all the examPeriods.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ExamPeriodDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ExamPeriods");
        return examPeriodRepository.findAll(pageable)
            .map(examPeriodMapper::toDto);
    }

    /**
     * Get one examPeriod by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ExamPeriodDTO findOne(Long id) {
        log.debug("Request to get ExamPeriod : {}", id);
        ExamPeriod examPeriod = examPeriodRepository.findOne(id);
        return examPeriodMapper.toDto(examPeriod);
    }

    /**
     * Delete the examPeriod by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ExamPeriod : {}", id);
        examPeriodRepository.delete(id);
        examPeriodSearchRepository.delete(id);
    }

    /**
     * Search for the examPeriod corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ExamPeriodDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ExamPeriods for query {}", query);
        Page<ExamPeriod> result = examPeriodSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(examPeriodMapper::toDto);
    }
}
