package irpbc.iteh.web.rest;

import com.codahale.metrics.annotation.Timed;
import irpbc.iteh.service.ExamService;
import irpbc.iteh.web.rest.errors.BadRequestAlertException;
import irpbc.iteh.web.rest.util.HeaderUtil;
import irpbc.iteh.web.rest.util.PaginationUtil;
import irpbc.iteh.service.dto.ExamDTO;
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
 * REST controller for managing Exam.
 */
@RestController
@RequestMapping("/api")
public class ExamResource {

    private final Logger log = LoggerFactory.getLogger(ExamResource.class);

    private static final String ENTITY_NAME = "exam";

    private final ExamService examService;

    public ExamResource(ExamService examService) {
        this.examService = examService;
    }

    /**
     * POST  /exams : Create a new exam.
     *
     * @param examDTO the examDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new examDTO, or with status 400 (Bad Request) if the exam has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/exams")
    @Timed
    public ResponseEntity<ExamDTO> createExam(@Valid @RequestBody ExamDTO examDTO) throws URISyntaxException {
        log.debug("REST request to save Exam : {}", examDTO);
        if (examDTO.getId() != null) {
            throw new BadRequestAlertException("A new exam cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExamDTO result = examService.save(examDTO);
        return ResponseEntity.created(new URI("/api/exams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /exams : Updates an existing exam.
     *
     * @param examDTO the examDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated examDTO,
     * or with status 400 (Bad Request) if the examDTO is not valid,
     * or with status 500 (Internal Server Error) if the examDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/exams")
    @Timed
    public ResponseEntity<ExamDTO> updateExam(@Valid @RequestBody ExamDTO examDTO) throws URISyntaxException {
        log.debug("REST request to update Exam : {}", examDTO);
        if (examDTO.getId() == null) {
            return createExam(examDTO);
        }
        ExamDTO result = examService.save(examDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, examDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /exams : get all the exams.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of exams in body
     */
    @GetMapping("/exams")
    @Timed
    public ResponseEntity<List<ExamDTO>> getAllExams(Pageable pageable) {
        log.debug("REST request to get a page of Exams");
        Page<ExamDTO> page = examService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/exams");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /exams/:id : get the "id" exam.
     *
     * @param id the id of the examDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the examDTO, or with status 404 (Not Found)
     */
    @GetMapping("/exams/{id}")
    @Timed
    public ResponseEntity<ExamDTO> getExam(@PathVariable Long id) {
        log.debug("REST request to get Exam : {}", id);
        ExamDTO examDTO = examService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(examDTO));
    }

    /**
     * DELETE  /exams/:id : delete the "id" exam.
     *
     * @param id the id of the examDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/exams/{id}")
    @Timed
    public ResponseEntity<Void> deleteExam(@PathVariable Long id) {
        log.debug("REST request to delete Exam : {}", id);
        examService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/exams?query=:query : search for the exam corresponding
     * to the query.
     *
     * @param query the query of the exam search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/exams")
    @Timed
    public ResponseEntity<List<ExamDTO>> searchExams(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Exams for query {}", query);
        Page<ExamDTO> page = examService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/exams");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
