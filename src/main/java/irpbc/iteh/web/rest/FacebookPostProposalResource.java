package irpbc.iteh.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import irpbc.iteh.service.FacebookPostProposalService;
import irpbc.iteh.service.dto.FacebookPostProposalDTO;
import irpbc.iteh.web.rest.util.HeaderUtil;
import irpbc.iteh.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PostMapping("/facebook-post-proposals/{id}/post")
    public ResponseEntity<Void> postToFacebook(@PathVariable Long id, @RequestBody String text) {
        facebookPostProposalService.postToFacebook(id, text);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
