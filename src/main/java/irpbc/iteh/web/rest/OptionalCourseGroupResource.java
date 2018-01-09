package irpbc.iteh.web.rest;

import com.codahale.metrics.annotation.Timed;
import irpbc.iteh.service.OptionalCourseGroupService;
import irpbc.iteh.web.rest.errors.BadRequestAlertException;
import irpbc.iteh.web.rest.util.HeaderUtil;
import irpbc.iteh.web.rest.util.PaginationUtil;
import irpbc.iteh.service.dto.OptionalCourseGroupDTO;
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
 * REST controller for managing OptionalCourseGroup.
 */
@RestController
@RequestMapping("/api")
public class OptionalCourseGroupResource {

    private final Logger log = LoggerFactory.getLogger(OptionalCourseGroupResource.class);

    private static final String ENTITY_NAME = "optionalCourseGroup";

    private final OptionalCourseGroupService optionalCourseGroupService;

    public OptionalCourseGroupResource(OptionalCourseGroupService optionalCourseGroupService) {
        this.optionalCourseGroupService = optionalCourseGroupService;
    }

    /**
     * POST  /optional-course-groups : Create a new optionalCourseGroup.
     *
     * @param optionalCourseGroupDTO the optionalCourseGroupDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new optionalCourseGroupDTO, or with status 400 (Bad Request) if the optionalCourseGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/optional-course-groups")
    @Timed
    public ResponseEntity<OptionalCourseGroupDTO> createOptionalCourseGroup(@Valid @RequestBody OptionalCourseGroupDTO optionalCourseGroupDTO) throws URISyntaxException {
        log.debug("REST request to save OptionalCourseGroup : {}", optionalCourseGroupDTO);
        if (optionalCourseGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new optionalCourseGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OptionalCourseGroupDTO result = optionalCourseGroupService.save(optionalCourseGroupDTO);
        return ResponseEntity.created(new URI("/api/optional-course-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /optional-course-groups : Updates an existing optionalCourseGroup.
     *
     * @param optionalCourseGroupDTO the optionalCourseGroupDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated optionalCourseGroupDTO,
     * or with status 400 (Bad Request) if the optionalCourseGroupDTO is not valid,
     * or with status 500 (Internal Server Error) if the optionalCourseGroupDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/optional-course-groups")
    @Timed
    public ResponseEntity<OptionalCourseGroupDTO> updateOptionalCourseGroup(@Valid @RequestBody OptionalCourseGroupDTO optionalCourseGroupDTO) throws URISyntaxException {
        log.debug("REST request to update OptionalCourseGroup : {}", optionalCourseGroupDTO);
        if (optionalCourseGroupDTO.getId() == null) {
            return createOptionalCourseGroup(optionalCourseGroupDTO);
        }
        OptionalCourseGroupDTO result = optionalCourseGroupService.save(optionalCourseGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, optionalCourseGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /optional-course-groups : get all the optionalCourseGroups.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of optionalCourseGroups in body
     */
    @GetMapping("/optional-course-groups")
    @Timed
    public ResponseEntity<List<OptionalCourseGroupDTO>> getAllOptionalCourseGroups(Pageable pageable) {
        log.debug("REST request to get a page of OptionalCourseGroups");
        Page<OptionalCourseGroupDTO> page = optionalCourseGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/optional-course-groups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /optional-course-groups/:id : get the "id" optionalCourseGroup.
     *
     * @param id the id of the optionalCourseGroupDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the optionalCourseGroupDTO, or with status 404 (Not Found)
     */
    @GetMapping("/optional-course-groups/{id}")
    @Timed
    public ResponseEntity<OptionalCourseGroupDTO> getOptionalCourseGroup(@PathVariable Long id) {
        log.debug("REST request to get OptionalCourseGroup : {}", id);
        OptionalCourseGroupDTO optionalCourseGroupDTO = optionalCourseGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(optionalCourseGroupDTO));
    }

    /**
     * DELETE  /optional-course-groups/:id : delete the "id" optionalCourseGroup.
     *
     * @param id the id of the optionalCourseGroupDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/optional-course-groups/{id}")
    @Timed
    public ResponseEntity<Void> deleteOptionalCourseGroup(@PathVariable Long id) {
        log.debug("REST request to delete OptionalCourseGroup : {}", id);
        optionalCourseGroupService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/optional-course-groups?query=:query : search for the optionalCourseGroup corresponding
     * to the query.
     *
     * @param query the query of the optionalCourseGroup search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/optional-course-groups")
    @Timed
    public ResponseEntity<List<OptionalCourseGroupDTO>> searchOptionalCourseGroups(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of OptionalCourseGroups for query {}", query);
        Page<OptionalCourseGroupDTO> page = optionalCourseGroupService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/optional-course-groups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
