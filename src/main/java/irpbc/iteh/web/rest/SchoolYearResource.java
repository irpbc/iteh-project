package irpbc.iteh.web.rest;

import com.codahale.metrics.annotation.Timed;
import irpbc.iteh.service.SchoolYearService;
import irpbc.iteh.web.rest.errors.BadRequestAlertException;
import irpbc.iteh.web.rest.util.HeaderUtil;
import irpbc.iteh.web.rest.util.PaginationUtil;
import irpbc.iteh.service.dto.SchoolYearDTO;
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
 * REST controller for managing SchoolYear.
 */
@RestController
@RequestMapping("/api")
public class SchoolYearResource {

    private final Logger log = LoggerFactory.getLogger(SchoolYearResource.class);

    private static final String ENTITY_NAME = "schoolYear";

    private final SchoolYearService schoolYearService;

    public SchoolYearResource(SchoolYearService schoolYearService) {
        this.schoolYearService = schoolYearService;
    }

    /**
     * POST  /school-years : Create a new schoolYear.
     *
     * @param schoolYearDTO the schoolYearDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new schoolYearDTO, or with status 400 (Bad Request) if the schoolYear has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/school-years")
    @Timed
    public ResponseEntity<SchoolYearDTO> createSchoolYear(@Valid @RequestBody SchoolYearDTO schoolYearDTO) throws URISyntaxException {
        log.debug("REST request to save SchoolYear : {}", schoolYearDTO);
        if (schoolYearDTO.getId() != null) {
            throw new BadRequestAlertException("A new schoolYear cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SchoolYearDTO result = schoolYearService.save(schoolYearDTO);
        return ResponseEntity.created(new URI("/api/school-years/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /school-years : Updates an existing schoolYear.
     *
     * @param schoolYearDTO the schoolYearDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated schoolYearDTO,
     * or with status 400 (Bad Request) if the schoolYearDTO is not valid,
     * or with status 500 (Internal Server Error) if the schoolYearDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/school-years")
    @Timed
    public ResponseEntity<SchoolYearDTO> updateSchoolYear(@Valid @RequestBody SchoolYearDTO schoolYearDTO) throws URISyntaxException {
        log.debug("REST request to update SchoolYear : {}", schoolYearDTO);
        if (schoolYearDTO.getId() == null) {
            return createSchoolYear(schoolYearDTO);
        }
        SchoolYearDTO result = schoolYearService.save(schoolYearDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, schoolYearDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /school-years : get all the schoolYears.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of schoolYears in body
     */
    @GetMapping("/school-years")
    @Timed
    public ResponseEntity<List<SchoolYearDTO>> getAllSchoolYears(Pageable pageable) {
        log.debug("REST request to get a page of SchoolYears");
        Page<SchoolYearDTO> page = schoolYearService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/school-years");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /school-years/:id : get the "id" schoolYear.
     *
     * @param id the id of the schoolYearDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the schoolYearDTO, or with status 404 (Not Found)
     */
    @GetMapping("/school-years/{id}")
    @Timed
    public ResponseEntity<SchoolYearDTO> getSchoolYear(@PathVariable Long id) {
        log.debug("REST request to get SchoolYear : {}", id);
        SchoolYearDTO schoolYearDTO = schoolYearService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(schoolYearDTO));
    }

    /**
     * DELETE  /school-years/:id : delete the "id" schoolYear.
     *
     * @param id the id of the schoolYearDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/school-years/{id}")
    @Timed
    public ResponseEntity<Void> deleteSchoolYear(@PathVariable Long id) {
        log.debug("REST request to delete SchoolYear : {}", id);
        schoolYearService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @PutMapping("/school-years/{id}/set-current")
    public ResponseEntity<Void> setCerrentSchoolYear(@PathVariable Long id) {
        schoolYearService.setCurrent(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * SEARCH  /_search/school-years?query=:query : search for the schoolYear corresponding
     * to the query.
     *
     * @param query the query of the schoolYear search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/school-years")
    @Timed
    public ResponseEntity<List<SchoolYearDTO>> searchSchoolYears(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SchoolYears for query {}", query);
        Page<SchoolYearDTO> page = schoolYearService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/school-years");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
