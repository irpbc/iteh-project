package irpbc.iteh.service;

import irpbc.iteh.domain.OptionalCourseGroup;
import irpbc.iteh.repository.OptionalCourseGroupRepository;
import irpbc.iteh.repository.search.OptionalCourseGroupSearchRepository;
import irpbc.iteh.service.dto.OptionalCourseGroupDTO;
import irpbc.iteh.service.mapper.OptionalCourseGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing OptionalCourseGroup.
 */
@Service
@Transactional
public class OptionalCourseGroupService {

    private final Logger log = LoggerFactory.getLogger(OptionalCourseGroupService.class);

    private final OptionalCourseGroupRepository optionalCourseGroupRepository;

    private final OptionalCourseGroupMapper optionalCourseGroupMapper;

    private final OptionalCourseGroupSearchRepository optionalCourseGroupSearchRepository;

    public OptionalCourseGroupService(OptionalCourseGroupRepository optionalCourseGroupRepository, OptionalCourseGroupMapper optionalCourseGroupMapper, OptionalCourseGroupSearchRepository optionalCourseGroupSearchRepository) {
        this.optionalCourseGroupRepository = optionalCourseGroupRepository;
        this.optionalCourseGroupMapper = optionalCourseGroupMapper;
        this.optionalCourseGroupSearchRepository = optionalCourseGroupSearchRepository;
    }

    /**
     * Save a optionalCourseGroup.
     *
     * @param optionalCourseGroupDTO the entity to save
     * @return the persisted entity
     */
    public OptionalCourseGroupDTO save(OptionalCourseGroupDTO optionalCourseGroupDTO) {
        log.debug("Request to save OptionalCourseGroup : {}", optionalCourseGroupDTO);
        OptionalCourseGroup optionalCourseGroup = optionalCourseGroupMapper.toEntity(optionalCourseGroupDTO);
        optionalCourseGroup = optionalCourseGroupRepository.save(optionalCourseGroup);
        OptionalCourseGroupDTO result = optionalCourseGroupMapper.toDto(optionalCourseGroup);
        optionalCourseGroupSearchRepository.save(optionalCourseGroup);
        return result;
    }

    /**
     * Get all the optionalCourseGroups.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<OptionalCourseGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OptionalCourseGroups");
        return optionalCourseGroupRepository.findAll(pageable)
            .map(optionalCourseGroupMapper::toDto);
    }

    /**
     * Get one optionalCourseGroup by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public OptionalCourseGroupDTO findOne(Long id) {
        log.debug("Request to get OptionalCourseGroup : {}", id);
        OptionalCourseGroup optionalCourseGroup = optionalCourseGroupRepository.findOne(id);
        return optionalCourseGroupMapper.toDto(optionalCourseGroup);
    }

    /**
     * Delete the optionalCourseGroup by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete OptionalCourseGroup : {}", id);
        optionalCourseGroupRepository.delete(id);
        optionalCourseGroupSearchRepository.delete(id);
    }

    /**
     * Search for the optionalCourseGroup corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<OptionalCourseGroupDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OptionalCourseGroups for query {}", query);
        Page<OptionalCourseGroup> result = optionalCourseGroupSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(optionalCourseGroupMapper::toDto);
    }
}
