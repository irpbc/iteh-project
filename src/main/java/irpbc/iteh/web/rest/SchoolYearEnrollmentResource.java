package irpbc.iteh.web.rest;

import com.codahale.metrics.annotation.Timed;
import irpbc.iteh.service.SchoolYearEnrollmentService;
import irpbc.iteh.web.rest.errors.BadRequestAlertException;
import irpbc.iteh.web.rest.util.HeaderUtil;
import irpbc.iteh.web.rest.util.PaginationUtil;
import irpbc.iteh.service.dto.SchoolYearEnrollmentDTO;
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
 * REST controller for managing SchoolYearEnrollment.
 */
@RestController
@RequestMapping("/api")
public class SchoolYearEnrollmentResource {

    private final Logger log = LoggerFactory.getLogger(SchoolYearEnrollmentResource.class);

    private static final String ENTITY_NAME = "schoolYearEnrollment";

    private final SchoolYearEnrollmentService schoolYearEnrollmentService;

    public SchoolYearEnrollmentResource(SchoolYearEnrollmentService schoolYearEnrollmentService) {
        this.schoolYearEnrollmentService = schoolYearEnrollmentService;
    }

    /**
     * POST  /school-year-enrollments : Create a new schoolYearEnrollment.
     *
     * @param schoolYearEnrollmentDTO the schoolYearEnrollmentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new schoolYearEnrollmentDTO, or with status 400 (Bad Request) if the schoolYearEnrollment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/school-year-enrollments")
    @Timed
    public ResponseEntity<SchoolYearEnrollmentDTO> createSchoolYearEnrollment(@Valid @RequestBody SchoolYearEnrollmentDTO schoolYearEnrollmentDTO) throws URISyntaxException {
        log.debug("REST request to save SchoolYearEnrollment : {}", schoolYearEnrollmentDTO);
        if (schoolYearEnrollmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new schoolYearEnrollment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SchoolYearEnrollmentDTO result = schoolYearEnrollmentService.save(schoolYearEnrollmentDTO);
        return ResponseEntity.created(new URI("/api/school-year-enrollments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /school-year-enrollments : Updates an existing schoolYearEnrollment.
     *
     * @param schoolYearEnrollmentDTO the schoolYearEnrollmentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated schoolYearEnrollmentDTO,
     * or with status 400 (Bad Request) if the schoolYearEnrollmentDTO is not valid,
     * or with status 500 (Internal Server Error) if the schoolYearEnrollmentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/school-year-enrollments")
    @Timed
    public ResponseEntity<SchoolYearEnrollmentDTO> updateSchoolYearEnrollment(@Valid @RequestBody SchoolYearEnrollmentDTO schoolYearEnrollmentDTO) throws URISyntaxException {
        log.debug("REST request to update SchoolYearEnrollment : {}", schoolYearEnrollmentDTO);
        if (schoolYearEnrollmentDTO.getId() == null) {
            return createSchoolYearEnrollment(schoolYearEnrollmentDTO);
        }
        SchoolYearEnrollmentDTO result = schoolYearEnrollmentService.save(schoolYearEnrollmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, schoolYearEnrollmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /school-year-enrollments : get all the schoolYearEnrollments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of schoolYearEnrollments in body
     */
    @GetMapping("/school-year-enrollments")
    @Timed
    public ResponseEntity<List<SchoolYearEnrollmentDTO>> getAllSchoolYearEnrollments(Pageable pageable) {
        log.debug("REST request to get a page of SchoolYearEnrollments");
        Page<SchoolYearEnrollmentDTO> page = schoolYearEnrollmentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/school-year-enrollments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /school-year-enrollments/:id : get the "id" schoolYearEnrollment.
     *
     * @param id the id of the schoolYearEnrollmentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the schoolYearEnrollmentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/school-year-enrollments/{id}")
    @Timed
    public ResponseEntity<SchoolYearEnrollmentDTO> getSchoolYearEnrollment(@PathVariable Long id) {
        log.debug("REST request to get SchoolYearEnrollment : {}", id);
        SchoolYearEnrollmentDTO schoolYearEnrollmentDTO = schoolYearEnrollmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(schoolYearEnrollmentDTO));
    }

    /**
     * DELETE  /school-year-enrollments/:id : delete the "id" schoolYearEnrollment.
     *
     * @param id the id of the schoolYearEnrollmentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/school-year-enrollments/{id}")
    @Timed
    public ResponseEntity<Void> deleteSchoolYearEnrollment(@PathVariable Long id) {
        log.debug("REST request to delete SchoolYearEnrollment : {}", id);
        schoolYearEnrollmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/school-year-enrollments?query=:query : search for the schoolYearEnrollment corresponding
     * to the query.
     *
     * @param query the query of the schoolYearEnrollment search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/school-year-enrollments")
    @Timed
    public ResponseEntity<List<SchoolYearEnrollmentDTO>> searchSchoolYearEnrollments(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SchoolYearEnrollments for query {}", query);
        Page<SchoolYearEnrollmentDTO> page = schoolYearEnrollmentService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/school-year-enrollments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
