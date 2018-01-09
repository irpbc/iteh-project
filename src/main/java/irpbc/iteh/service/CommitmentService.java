package irpbc.iteh.service;

import irpbc.iteh.domain.Commitment;
import irpbc.iteh.repository.CommitmentRepository;
import irpbc.iteh.repository.search.CommitmentSearchRepository;
import irpbc.iteh.service.dto.CommitmentDTO;
import irpbc.iteh.service.mapper.CommitmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Commitment.
 */
@Service
@Transactional
public class CommitmentService {

    private final Logger log = LoggerFactory.getLogger(CommitmentService.class);

    private final CommitmentRepository commitmentRepository;

    private final CommitmentMapper commitmentMapper;

    private final CommitmentSearchRepository commitmentSearchRepository;

    public CommitmentService(CommitmentRepository commitmentRepository, CommitmentMapper commitmentMapper, CommitmentSearchRepository commitmentSearchRepository) {
        this.commitmentRepository = commitmentRepository;
        this.commitmentMapper = commitmentMapper;
        this.commitmentSearchRepository = commitmentSearchRepository;
    }

    /**
     * Save a commitment.
     *
     * @param commitmentDTO the entity to save
     * @return the persisted entity
     */
    public CommitmentDTO save(CommitmentDTO commitmentDTO) {
        log.debug("Request to save Commitment : {}", commitmentDTO);
        Commitment commitment = commitmentMapper.toEntity(commitmentDTO);
        commitment = commitmentRepository.save(commitment);
        CommitmentDTO result = commitmentMapper.toDto(commitment);
        commitmentSearchRepository.save(commitment);
        return result;
    }

    /**
     * Get all the commitments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommitmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Commitments");
        return commitmentRepository.findAll(pageable)
            .map(commitmentMapper::toDto);
    }

    /**
     * Get one commitment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public CommitmentDTO findOne(Long id) {
        log.debug("Request to get Commitment : {}", id);
        Commitment commitment = commitmentRepository.findOne(id);
        return commitmentMapper.toDto(commitment);
    }

    /**
     * Delete the commitment by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Commitment : {}", id);
        commitmentRepository.delete(id);
        commitmentSearchRepository.delete(id);
    }

    /**
     * Search for the commitment corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommitmentDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Commitments for query {}", query);
        Page<Commitment> result = commitmentSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(commitmentMapper::toDto);
    }
}
