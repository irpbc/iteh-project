package irpbc.iteh.web.rest;

import com.codahale.metrics.annotation.Timed;
import irpbc.iteh.service.FacebookPostProposalService;
import irpbc.iteh.web.rest.errors.BadRequestAlertException;
import irpbc.iteh.web.rest.util.HeaderUtil;
import irpbc.iteh.web.rest.util.PaginationUtil;
import irpbc.iteh.service.dto.FacebookPostProposalDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing FacebookPostProposal.
 */
@RestController
@RequestMapping("/api")
public class FacebookPostProposalResource {

    private final Logger log = LoggerFactory.getLogger(FacebookPostProposalResource.class);

    private static final String ENTITY_NAME = "facebookPostProposal";

    private final FacebookPostProposalService facebookPostProposalService;

    public FacebookPostProposalResource(FacebookPostProposalService facebookPostProposalService) {
        this.facebookPostProposalService = facebookPostProposalService;
    }

    /**
     * POST  /facebook-post-proposals : Create a new facebookPostProposal.
     *
     * @param facebookPostProposalDTO the facebookPostProposalDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new facebookPostProposalDTO, or with status 400 (Bad Request) if the facebookPostProposal has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/facebook-post-proposals")
    @Timed
    public ResponseEntity<FacebookPostProposalDTO> createFacebookPostProposal(@Valid @RequestBody FacebookPostProposalDTO facebookPostProposalDTO) throws URISyntaxException {
        log.debug("REST request to save FacebookPostProposal : {}", facebookPostProposalDTO);
        if (facebookPostProposalDTO.getId() != null) {
            throw new BadRequestAlertException("A new facebookPostProposal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FacebookPostProposalDTO result = facebookPostProposalService.save(facebookPostProposalDTO);
        return ResponseEntity.created(new URI("/api/facebook-post-proposals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /facebook-post-proposals : Updates an existing facebookPostProposal.
     *
     * @param facebookPostProposalDTO the facebookPostProposalDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated facebookPostProposalDTO,
     * or with status 400 (Bad Request) if the facebookPostProposalDTO is not valid,
     * or with status 500 (Internal Server Error) if the facebookPostProposalDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/facebook-post-proposals")
    @Timed
    public ResponseEntity<FacebookPostProposalDTO> updateFacebookPostProposal(@Valid @RequestBody FacebookPostProposalDTO facebookPostProposalDTO) throws URISyntaxException {
        log.debug("REST request to update FacebookPostProposal : {}", facebookPostProposalDTO);
        if (facebookPostProposalDTO.getId() == null) {
            return createFacebookPostProposal(facebookPostProposalDTO);
        }
        FacebookPostProposalDTO result = facebookPostProposalService.save(facebookPostProposalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, facebookPostProposalDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /facebook-post-proposals : get all the facebookPostProposals.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of facebookPostProposals in body
     */
    @GetMapping("/facebook-post-proposals")
    @Timed
    public ResponseEntity<List<FacebookPostProposalDTO>> getAllFacebookPostProposals(Pageable pageable) {
        log.debug("REST request to get a page of FacebookPostProposals");
        Page<FacebookPostProposalDTO> page = facebookPostProposalService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/facebook-post-proposals");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /facebook-post-proposals/:id : get the "id" facebookPostProposal.
     *
     * @param id the id of the facebookPostProposalDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the facebookPostProposalDTO, or with status 404 (Not Found)
     */
    @GetMapping("/facebook-post-proposals/{id}")
    @Timed
    public ResponseEntity<FacebookPostProposalDTO> getFacebookPostProposal(@PathVariable Long id) {
        log.debug("REST request to get FacebookPostProposal : {}", id);
        FacebookPostProposalDTO facebookPostProposalDTO = facebookPostProposalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(facebookPostProposalDTO));
    }

    /**
     * DELETE  /facebook-post-proposals/:id : delete the "id" facebookPostProposal.
     *
     * @param id the id of the facebookPostProposalDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/facebook-post-proposals/{id}")
    @Timed
    public ResponseEntity<Void> deleteFacebookPostProposal(@PathVariable Long id) {
        log.debug("REST request to delete FacebookPostProposal : {}", id);
        facebookPostProposalService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
