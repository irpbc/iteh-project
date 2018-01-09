package irpbc.iteh.web.rest;

import com.codahale.metrics.annotation.Timed;
import irpbc.iteh.service.CourseEnrollmentService;
import irpbc.iteh.web.rest.errors.BadRequestAlertException;
import irpbc.iteh.web.rest.util.HeaderUtil;
import irpbc.iteh.web.rest.util.PaginationUtil;
import irpbc.iteh.service.dto.CourseEnrollmentDTO;
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
 * REST controller for managing CourseEnrollment.
 */
@RestController
@RequestMapping("/api")
public class CourseEnrollmentResource {

    private final Logger log = LoggerFactory.getLogger(CourseEnrollmentResource.class);

    private static final String ENTITY_NAME = "courseEnrollment";

    private final CourseEnrollmentService courseEnrollmentService;

    public CourseEnrollmentResource(CourseEnrollmentService courseEnrollmentService) {
        this.courseEnrollmentService = courseEnrollmentService;
    }

    /**
     * POST  /course-enrollments : Create a new courseEnrollment.
     *
     * @param courseEnrollmentDTO the courseEnrollmentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new courseEnrollmentDTO, or with status 400 (Bad Request) if the courseEnrollment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/course-enrollments")
    @Timed
    public ResponseEntity<CourseEnrollmentDTO> createCourseEnrollment(@Valid @RequestBody CourseEnrollmentDTO courseEnrollmentDTO) throws URISyntaxException {
        log.debug("REST request to save CourseEnrollment : {}", courseEnrollmentDTO);
        if (courseEnrollmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new courseEnrollment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CourseEnrollmentDTO result = courseEnrollmentService.save(courseEnrollmentDTO);
        return ResponseEntity.created(new URI("/api/course-enrollments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /course-enrollments : Updates an existing courseEnrollment.
     *
     * @param courseEnrollmentDTO the courseEnrollmentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated courseEnrollmentDTO,
     * or with status 400 (Bad Request) if the courseEnrollmentDTO is not valid,
     * or with status 500 (Internal Server Error) if the courseEnrollmentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/course-enrollments")
    @Timed
    public ResponseEntity<CourseEnrollmentDTO> updateCourseEnrollment(@Valid @RequestBody CourseEnrollmentDTO courseEnrollmentDTO) throws URISyntaxException {
        log.debug("REST request to update CourseEnrollment : {}", courseEnrollmentDTO);
        if (courseEnrollmentDTO.getId() == null) {
            return createCourseEnrollment(courseEnrollmentDTO);
        }
        CourseEnrollmentDTO result = courseEnrollmentService.save(courseEnrollmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, courseEnrollmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /course-enrollments : get all the courseEnrollments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of courseEnrollments in body
     */
    @GetMapping("/course-enrollments")
    @Timed
    public ResponseEntity<List<CourseEnrollmentDTO>> getAllCourseEnrollments(Pageable pageable) {
        log.debug("REST request to get a page of CourseEnrollments");
        Page<CourseEnrollmentDTO> page = courseEnrollmentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/course-enrollments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /course-enrollments/:id : get the "id" courseEnrollment.
     *
     * @param id the id of the courseEnrollmentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the courseEnrollmentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/course-enrollments/{id}")
    @Timed
    public ResponseEntity<CourseEnrollmentDTO> getCourseEnrollment(@PathVariable Long id) {
        log.debug("REST request to get CourseEnrollment : {}", id);
        CourseEnrollmentDTO courseEnrollmentDTO = courseEnrollmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(courseEnrollmentDTO));
    }

    /**
     * DELETE  /course-enrollments/:id : delete the "id" courseEnrollment.
     *
     * @param id the id of the courseEnrollmentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/course-enrollments/{id}")
    @Timed
    public ResponseEntity<Void> deleteCourseEnrollment(@PathVariable Long id) {
        log.debug("REST request to delete CourseEnrollment : {}", id);
        courseEnrollmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/course-enrollments?query=:query : search for the courseEnrollment corresponding
     * to the query.
     *
     * @param query the query of the courseEnrollment search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/course-enrollments")
    @Timed
    public ResponseEntity<List<CourseEnrollmentDTO>> searchCourseEnrollments(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CourseEnrollments for query {}", query);
        Page<CourseEnrollmentDTO> page = courseEnrollmentService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/course-enrollments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
