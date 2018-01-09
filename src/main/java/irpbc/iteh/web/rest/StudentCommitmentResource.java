package irpbc.iteh.web.rest;

import com.codahale.metrics.annotation.Timed;
import irpbc.iteh.service.StudentCommitmentService;
import irpbc.iteh.web.rest.errors.BadRequestAlertException;
import irpbc.iteh.web.rest.util.HeaderUtil;
import irpbc.iteh.web.rest.util.PaginationUtil;
import irpbc.iteh.service.dto.StudentCommitmentDTO;
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
 * REST controller for managing StudentCommitment.
 */
@RestController
@RequestMapping("/api")
public class StudentCommitmentResource {

    private final Logger log = LoggerFactory.getLogger(StudentCommitmentResource.class);

    private static final String ENTITY_NAME = "studentCommitment";

    private final StudentCommitmentService studentCommitmentService;

    public StudentCommitmentResource(StudentCommitmentService studentCommitmentService) {
        this.studentCommitmentService = studentCommitmentService;
    }

    /**
     * POST  /student-commitments : Create a new studentCommitment.
     *
     * @param studentCommitmentDTO the studentCommitmentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new studentCommitmentDTO, or with status 400 (Bad Request) if the studentCommitment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/student-commitments")
    @Timed
    public ResponseEntity<StudentCommitmentDTO> createStudentCommitment(@Valid @RequestBody StudentCommitmentDTO studentCommitmentDTO) throws URISyntaxException {
        log.debug("REST request to save StudentCommitment : {}", studentCommitmentDTO);
        if (studentCommitmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new studentCommitment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StudentCommitmentDTO result = studentCommitmentService.save(studentCommitmentDTO);
        return ResponseEntity.created(new URI("/api/student-commitments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /student-commitments : Updates an existing studentCommitment.
     *
     * @param studentCommitmentDTO the studentCommitmentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated studentCommitmentDTO,
     * or with status 400 (Bad Request) if the studentCommitmentDTO is not valid,
     * or with status 500 (Internal Server Error) if the studentCommitmentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/student-commitments")
    @Timed
    public ResponseEntity<StudentCommitmentDTO> updateStudentCommitment(@Valid @RequestBody StudentCommitmentDTO studentCommitmentDTO) throws URISyntaxException {
        log.debug("REST request to update StudentCommitment : {}", studentCommitmentDTO);
        if (studentCommitmentDTO.getId() == null) {
            return createStudentCommitment(studentCommitmentDTO);
        }
        StudentCommitmentDTO result = studentCommitmentService.save(studentCommitmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, studentCommitmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /student-commitments : get all the studentCommitments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of studentCommitments in body
     */
    @GetMapping("/student-commitments")
    @Timed
    public ResponseEntity<List<StudentCommitmentDTO>> getAllStudentCommitments(Pageable pageable) {
        log.debug("REST request to get a page of StudentCommitments");
        Page<StudentCommitmentDTO> page = studentCommitmentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/student-commitments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /student-commitments/:id : get the "id" studentCommitment.
     *
     * @param id the id of the studentCommitmentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the studentCommitmentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/student-commitments/{id}")
    @Timed
    public ResponseEntity<StudentCommitmentDTO> getStudentCommitment(@PathVariable Long id) {
        log.debug("REST request to get StudentCommitment : {}", id);
        StudentCommitmentDTO studentCommitmentDTO = studentCommitmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(studentCommitmentDTO));
    }

    /**
     * DELETE  /student-commitments/:id : delete the "id" studentCommitment.
     *
     * @param id the id of the studentCommitmentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/student-commitments/{id}")
    @Timed
    public ResponseEntity<Void> deleteStudentCommitment(@PathVariable Long id) {
        log.debug("REST request to delete StudentCommitment : {}", id);
        studentCommitmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/student-commitments?query=:query : search for the studentCommitment corresponding
     * to the query.
     *
     * @param query the query of the studentCommitment search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/student-commitments")
    @Timed
    public ResponseEntity<List<StudentCommitmentDTO>> searchStudentCommitments(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of StudentCommitments for query {}", query);
        Page<StudentCommitmentDTO> page = studentCommitmentService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/student-commitments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
