package irpbc.iteh.service;

import irpbc.iteh.domain.Lecturer;
import irpbc.iteh.repository.LecturerRepository;
import irpbc.iteh.repository.search.LecturerSearchRepository;
import irpbc.iteh.service.dto.LecturerDTO;
import irpbc.iteh.service.mapper.LecturerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Lecturer.
 */
@Service
@Transactional
public class LecturerService {

    private final Logger log = LoggerFactory.getLogger(LecturerService.class);

    private final LecturerRepository lecturerRepository;

    private final LecturerMapper lecturerMapper;

    private final LecturerSearchRepository lecturerSearchRepository;

    public LecturerService(LecturerRepository lecturerRepository, LecturerMapper lecturerMapper, LecturerSearchRepository lecturerSearchRepository) {
        this.lecturerRepository = lecturerRepository;
        this.lecturerMapper = lecturerMapper;
        this.lecturerSearchRepository = lecturerSearchRepository;
    }

    /**
     * Save a lecturer.
     *
     * @param lecturerDTO the entity to save
     * @return the persisted entity
     */
    public LecturerDTO save(LecturerDTO lecturerDTO) {
        log.debug("Request to save Lecturer : {}", lecturerDTO);
        Lecturer lecturer = lecturerMapper.toEntity(lecturerDTO);
        lecturer = lecturerRepository.save(lecturer);
        LecturerDTO result = lecturerMapper.toDto(lecturer);
        lecturerSearchRepository.save(lecturer);
        return result;
    }

    /**
     * Get all the lecturers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<LecturerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Lecturers");
        return lecturerRepository.findAll(pageable)
            .map(lecturerMapper::toDto);
    }

    /**
     * Get one lecturer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public LecturerDTO findOne(Long id) {
        log.debug("Request to get Lecturer : {}", id);
        Lecturer lecturer = lecturerRepository.findOne(id);
        return lecturerMapper.toDto(lecturer);
    }

    /**
     * Delete the lecturer by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Lecturer : {}", id);
        lecturerRepository.delete(id);
        lecturerSearchRepository.delete(id);
    }

    /**
     * Search for the lecturer corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<LecturerDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Lecturers for query {}", query);
        Page<Lecturer> result = lecturerSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(lecturerMapper::toDto);
    }
}
