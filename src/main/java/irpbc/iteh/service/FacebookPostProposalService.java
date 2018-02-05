package irpbc.iteh.service;

import irpbc.iteh.domain.FacebookPostProposal;
import irpbc.iteh.repository.FacebookPostProposalRepository;
import irpbc.iteh.service.dto.FacebookPostProposalDTO;
import irpbc.iteh.service.mapper.FacebookPostProposalMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing FacebookPostProposal.
 */
@Service
@Transactional
public class FacebookPostProposalService {

    private final Logger log = LoggerFactory.getLogger(FacebookPostProposalService.class);

    private final FacebookPostProposalRepository facebookPostProposalRepository;
    private final FacebookPostProposalMapper facebookPostProposalMapper;

    public FacebookPostProposalService(FacebookPostProposalRepository facebookPostProposalRepository,
                                       FacebookPostProposalMapper facebookPostProposalMapper) {
        this.facebookPostProposalRepository = facebookPostProposalRepository;
        this.facebookPostProposalMapper = facebookPostProposalMapper;
    }

    /**
     * Save a facebookPostProposal.
     *
     * @param facebookPostProposalDTO the entity to save
     * @return the persisted entity
     */
    public FacebookPostProposalDTO save(FacebookPostProposalDTO facebookPostProposalDTO) {
        log.debug("Request to save FacebookPostProposal : {}", facebookPostProposalDTO);
        FacebookPostProposal facebookPostProposal = facebookPostProposalMapper.toEntity(facebookPostProposalDTO);
        facebookPostProposal = facebookPostProposalRepository.save(facebookPostProposal);
        FacebookPostProposalDTO result = facebookPostProposalMapper.toDto(facebookPostProposal);
        return result;
    }

    /**
     * Get all the facebookPostProposals.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FacebookPostProposalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FacebookPostProposals");
        return facebookPostProposalRepository.findAll(pageable)
            .map(facebookPostProposalMapper::toDto);
    }

    /**
     * Get one facebookPostProposal by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public FacebookPostProposalDTO findOne(Long id) {
        log.debug("Request to get FacebookPostProposal : {}", id);
        FacebookPostProposal facebookPostProposal = facebookPostProposalRepository.findOne(id);
        return facebookPostProposalMapper.toDto(facebookPostProposal);
    }

    /**
     * Delete the facebookPostProposal by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete FacebookPostProposal : {}", id);
        facebookPostProposalRepository.delete(id);
    }
}
