package irpbc.iteh.web.rest;

import com.codahale.metrics.annotation.Timed;
import irpbc.iteh.service.LecturerService;
import irpbc.iteh.web.rest.errors.BadRequestAlertException;
import irpbc.iteh.web.rest.util.HeaderUtil;
import irpbc.iteh.web.rest.util.PaginationUtil;
import irpbc.iteh.service.dto.LecturerDTO;
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
 * REST controller for managing Lecturer.
 */
@RestController
@RequestMapping("/api")
public class LecturerResource {

    private final Logger log = LoggerFactory.getLogger(LecturerResource.class);

    private static final String ENTITY_NAME = "lecturer";

    private final LecturerService lecturerService;

    public LecturerResource(LecturerService lecturerService) {
        this.lecturerService = lecturerService;
    }

    /**
     * POST  /lecturers : Create a new lecturer.
     *
     * @param lecturerDTO the lecturerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lecturerDTO, or with status 400 (Bad Request) if the lecturer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lecturers")
    @Timed
    public ResponseEntity<LecturerDTO> createLecturer(@Valid @RequestBody LecturerDTO lecturerDTO) throws URISyntaxException {
        log.debug("REST request to save Lecturer : {}", lecturerDTO);
        if (lecturerDTO.getId() != null) {
            throw new BadRequestAlertException("A new lecturer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LecturerDTO result = lecturerService.save(lecturerDTO);
        return ResponseEntity.created(new URI("/api/lecturers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lecturers : Updates an existing lecturer.
     *
     * @param lecturerDTO the lecturerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lecturerDTO,
     * or with status 400 (Bad Request) if the lecturerDTO is not valid,
     * or with status 500 (Internal Server Error) if the lecturerDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lecturers")
    @Timed
    public ResponseEntity<LecturerDTO> updateLecturer(@Valid @RequestBody LecturerDTO lecturerDTO) throws URISyntaxException {
        log.debug("REST request to update Lecturer : {}", lecturerDTO);
        if (lecturerDTO.getId() == null) {
            return createLecturer(lecturerDTO);
        }
        LecturerDTO result = lecturerService.save(lecturerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lecturerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lecturers : get all the lecturers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of lecturers in body
     */
    @GetMapping("/lecturers")
    @Timed
    public ResponseEntity<List<LecturerDTO>> getAllLecturers(Pageable pageable) {
        log.debug("REST request to get a page of Lecturers");
        Page<LecturerDTO> page = lecturerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lecturers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /lecturers/:id : get the "id" lecturer.
     *
     * @param id the id of the lecturerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lecturerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/lecturers/{id}")
    @Timed
    public ResponseEntity<LecturerDTO> getLecturer(@PathVariable Long id) {
        log.debug("REST request to get Lecturer : {}", id);
        LecturerDTO lecturerDTO = lecturerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(lecturerDTO));
    }

    /**
     * DELETE  /lecturers/:id : delete the "id" lecturer.
     *
     * @param id the id of the lecturerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lecturers/{id}")
    @Timed
    public ResponseEntity<Void> deleteLecturer(@PathVariable Long id) {
        log.debug("REST request to delete Lecturer : {}", id);
        lecturerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/lecturers?query=:query : search for the lecturer corresponding
     * to the query.
     *
     * @param query the query of the lecturer search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/lecturers")
    @Timed
    public ResponseEntity<List<LecturerDTO>> searchLecturers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Lecturers for query {}", query);
        Page<LecturerDTO> page = lecturerService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/lecturers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
