package irpbc.iteh.web.rest;

import com.codahale.metrics.annotation.Timed;
import irpbc.iteh.service.ExamPeriodService;
import irpbc.iteh.web.rest.errors.BadRequestAlertException;
import irpbc.iteh.web.rest.util.HeaderUtil;
import irpbc.iteh.web.rest.util.PaginationUtil;
import irpbc.iteh.service.dto.ExamPeriodDTO;
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
 * REST controller for managing ExamPeriod.
 */
@RestController
@RequestMapping("/api")
public class ExamPeriodResource {

    private final Logger log = LoggerFactory.getLogger(ExamPeriodResource.class);

    private static final String ENTITY_NAME = "examPeriod";

    private final ExamPeriodService examPeriodService;

    public ExamPeriodResource(ExamPeriodService examPeriodService) {
        this.examPeriodService = examPeriodService;
    }

    /**
     * POST  /exam-periods : Create a new examPeriod.
     *
     * @param examPeriodDTO the examPeriodDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new examPeriodDTO, or with status 400 (Bad Request) if the examPeriod has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/exam-periods")
    @Timed
    public ResponseEntity<ExamPeriodDTO> createExamPeriod(@Valid @RequestBody ExamPeriodDTO examPeriodDTO) throws URISyntaxException {
        log.debug("REST request to save ExamPeriod : {}", examPeriodDTO);
        if (examPeriodDTO.getId() != null) {
            throw new BadRequestAlertException("A new examPeriod cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExamPeriodDTO result = examPeriodService.save(examPeriodDTO);
        return ResponseEntity.created(new URI("/api/exam-periods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /exam-periods : Updates an existing examPeriod.
     *
     * @param examPeriodDTO the examPeriodDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated examPeriodDTO,
     * or with status 400 (Bad Request) if the examPeriodDTO is not valid,
     * or with status 500 (Internal Server Error) if the examPeriodDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/exam-periods")
    @Timed
    public ResponseEntity<ExamPeriodDTO> updateExamPeriod(@Valid @RequestBody ExamPeriodDTO examPeriodDTO) throws URISyntaxException {
        log.debug("REST request to update ExamPeriod : {}", examPeriodDTO);
        if (examPeriodDTO.getId() == null) {
            return createExamPeriod(examPeriodDTO);
        }
        ExamPeriodDTO result = examPeriodService.save(examPeriodDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, examPeriodDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /exam-periods : get all the examPeriods.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of examPeriods in body
     */
    @GetMapping("/exam-periods")
    @Timed
    public ResponseEntity<List<ExamPeriodDTO>> getAllExamPeriods(Pageable pageable) {
        log.debug("REST request to get a page of ExamPeriods");
        Page<ExamPeriodDTO> page = examPeriodService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/exam-periods");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /exam-periods/:id : get the "id" examPeriod.
     *
     * @param id the id of the examPeriodDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the examPeriodDTO, or with status 404 (Not Found)
     */
    @GetMapping("/exam-periods/{id}")
    @Timed
    public ResponseEntity<ExamPeriodDTO> getExamPeriod(@PathVariable Long id) {
        log.debug("REST request to get ExamPeriod : {}", id);
        ExamPeriodDTO examPeriodDTO = examPeriodService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(examPeriodDTO));
    }

    /**
     * DELETE  /exam-periods/:id : delete the "id" examPeriod.
     *
     * @param id the id of the examPeriodDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/exam-periods/{id}")
    @Timed
    public ResponseEntity<Void> deleteExamPeriod(@PathVariable Long id) {
        log.debug("REST request to delete ExamPeriod : {}", id);
        examPeriodService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/exam-periods?query=:query : search for the examPeriod corresponding
     * to the query.
     *
     * @param query the query of the examPeriod search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/exam-periods")
    @Timed
    public ResponseEntity<List<ExamPeriodDTO>> searchExamPeriods(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ExamPeriods for query {}", query);
        Page<ExamPeriodDTO> page = examPeriodService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/exam-periods");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
