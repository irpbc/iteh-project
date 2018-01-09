package irpbc.iteh.web.rest;

import com.codahale.metrics.annotation.Timed;
import irpbc.iteh.service.CommitmentService;
import irpbc.iteh.web.rest.errors.BadRequestAlertException;
import irpbc.iteh.web.rest.util.HeaderUtil;
import irpbc.iteh.web.rest.util.PaginationUtil;
import irpbc.iteh.service.dto.CommitmentDTO;
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
 * REST controller for managing Commitment.
 */
@RestController
@RequestMapping("/api")
public class CommitmentResource {

    private final Logger log = LoggerFactory.getLogger(CommitmentResource.class);

    private static final String ENTITY_NAME = "commitment";

    private final CommitmentService commitmentService;

    public CommitmentResource(CommitmentService commitmentService) {
        this.commitmentService = commitmentService;
    }

    /**
     * POST  /commitments : Create a new commitment.
     *
     * @param commitmentDTO the commitmentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commitmentDTO, or with status 400 (Bad Request) if the commitment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commitments")
    @Timed
    public ResponseEntity<CommitmentDTO> createCommitment(@Valid @RequestBody CommitmentDTO commitmentDTO) throws URISyntaxException {
        log.debug("REST request to save Commitment : {}", commitmentDTO);
        if (commitmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new commitment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommitmentDTO result = commitmentService.save(commitmentDTO);
        return ResponseEntity.created(new URI("/api/commitments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commitments : Updates an existing commitment.
     *
     * @param commitmentDTO the commitmentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commitmentDTO,
     * or with status 400 (Bad Request) if the commitmentDTO is not valid,
     * or with status 500 (Internal Server Error) if the commitmentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commitments")
    @Timed
    public ResponseEntity<CommitmentDTO> updateCommitment(@Valid @RequestBody CommitmentDTO commitmentDTO) throws URISyntaxException {
        log.debug("REST request to update Commitment : {}", commitmentDTO);
        if (commitmentDTO.getId() == null) {
            return createCommitment(commitmentDTO);
        }
        CommitmentDTO result = commitmentService.save(commitmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commitmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commitments : get all the commitments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of commitments in body
     */
    @GetMapping("/commitments")
    @Timed
    public ResponseEntity<List<CommitmentDTO>> getAllCommitments(Pageable pageable) {
        log.debug("REST request to get a page of Commitments");
        Page<CommitmentDTO> page = commitmentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/commitments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /commitments/:id : get the "id" commitment.
     *
     * @param id the id of the commitmentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commitmentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/commitments/{id}")
    @Timed
    public ResponseEntity<CommitmentDTO> getCommitment(@PathVariable Long id) {
        log.debug("REST request to get Commitment : {}", id);
        CommitmentDTO commitmentDTO = commitmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(commitmentDTO));
    }

    /**
     * DELETE  /commitments/:id : delete the "id" commitment.
     *
     * @param id the id of the commitmentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commitments/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommitment(@PathVariable Long id) {
        log.debug("REST request to delete Commitment : {}", id);
        commitmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/commitments?query=:query : search for the commitment corresponding
     * to the query.
     *
     * @param query the query of the commitment search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/commitments")
    @Timed
    public ResponseEntity<List<CommitmentDTO>> searchCommitments(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Commitments for query {}", query);
        Page<CommitmentDTO> page = commitmentService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/commitments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
