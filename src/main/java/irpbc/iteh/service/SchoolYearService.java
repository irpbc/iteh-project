package irpbc.iteh.service;

import irpbc.iteh.domain.SchoolYear;
import irpbc.iteh.repository.SchoolYearRepository;
import irpbc.iteh.repository.search.SchoolYearSearchRepository;
import irpbc.iteh.service.dto.SchoolYearDTO;
import irpbc.iteh.service.mapper.SchoolYearMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing SchoolYear.
 */
@Service
@Transactional
public class SchoolYearService {

    private final Logger log = LoggerFactory.getLogger(SchoolYearService.class);

    private final SchoolYearRepository schoolYearRepository;

    private final SchoolYearMapper schoolYearMapper;

    private final SchoolYearSearchRepository schoolYearSearchRepository;

    public SchoolYearService(SchoolYearRepository schoolYearRepository, SchoolYearMapper schoolYearMapper, SchoolYearSearchRepository schoolYearSearchRepository) {
        this.schoolYearRepository = schoolYearRepository;
        this.schoolYearMapper = schoolYearMapper;
        this.schoolYearSearchRepository = schoolYearSearchRepository;
    }

    /**
     * Save a schoolYear.
     *
     * @param schoolYearDTO the entity to save
     * @return the persisted entity
     */
    public SchoolYearDTO save(SchoolYearDTO schoolYearDTO) {
        log.debug("Request to save SchoolYear : {}", schoolYearDTO);
        SchoolYear schoolYear = schoolYearMapper.toEntity(schoolYearDTO);
        schoolYear = schoolYearRepository.save(schoolYear);
        SchoolYearDTO result = schoolYearMapper.toDto(schoolYear);
        schoolYearSearchRepository.save(schoolYear);
        return result;
    }

    /**
     * Get all the schoolYears.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SchoolYearDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SchoolYears");
        return schoolYearRepository.findAll(pageable)
            .map(schoolYearMapper::toDto);
    }

    /**
     * Get one schoolYear by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public SchoolYearDTO findOne(Long id) {
        log.debug("Request to get SchoolYear : {}", id);
        SchoolYear schoolYear = schoolYearRepository.findOne(id);
        return schoolYearMapper.toDto(schoolYear);
    }

    /**
     * Delete the schoolYear by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SchoolYear : {}", id);
        schoolYearRepository.delete(id);
        schoolYearSearchRepository.delete(id);
    }

    /**
     * Search for the schoolYear corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SchoolYearDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SchoolYears for query {}", query);
        Page<SchoolYear> result = schoolYearSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(schoolYearMapper::toDto);
    }
}
