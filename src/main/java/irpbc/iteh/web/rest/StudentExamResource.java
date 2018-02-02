package irpbc.iteh.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import irpbc.iteh.service.StudentExamService;
import irpbc.iteh.service.dto.ExamApplicationPageData;
import irpbc.iteh.service.dto.StudentExamDTO;
import irpbc.iteh.web.rest.errors.BadRequestAlertException;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing StudentExam.
 */
@RestController
@RequestMapping("/api")
public class StudentExamResource {

    private final Logger log = LoggerFactory.getLogger(StudentExamResource.class);

    private static final String ENTITY_NAME = "studentExam";

    private final StudentExamService studentExamService;

    public StudentExamResource(StudentExamService studentExamService) {
        this.studentExamService = studentExamService;
    }

    /**
     * POST  /student-exams : Create a new studentExam.
     *
     * @param studentExamDTO the studentExamDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new studentExamDTO, or with status 400
     * (Bad Request) if the studentExam has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/student-exams")
    @Timed
    public ResponseEntity<StudentExamDTO> createStudentExam(@Valid @RequestBody StudentExamDTO studentExamDTO) throws URISyntaxException {
        log.debug("REST request to save StudentExam : {}", studentExamDTO);
        if (studentExamDTO.getId() != null) {
            throw new BadRequestAlertException("A new studentExam cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StudentExamDTO result = studentExamService.save(studentExamDTO);
        return ResponseEntity.created(new URI("/api/student-exams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /student-exams : Updates an existing studentExam.
     *
     * @param studentExamDTO the studentExamDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated studentExamDTO,
     * or with status 400 (Bad Request) if the studentExamDTO is not valid,
     * or with status 500 (Internal Server Error) if the studentExamDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/student-exams")
    @Timed
    public ResponseEntity<StudentExamDTO> updateStudentExam(@Valid @RequestBody StudentExamDTO studentExamDTO) throws URISyntaxException {
        log.debug("REST request to update StudentExam : {}", studentExamDTO);
        if (studentExamDTO.getId() == null) {
            return createStudentExam(studentExamDTO);
        }
        StudentExamDTO result = studentExamService.save(studentExamDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, studentExamDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /student-exams : get all the studentExams.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of studentExams in body
     */
    @GetMapping("/student-exams")
    @Timed
    public ResponseEntity<List<StudentExamDTO>> getAllStudentExams(Pageable pageable) {
        log.debug("REST request to get a page of StudentExams");
        Page<StudentExamDTO> page = studentExamService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/student-exams");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /student-exams/:id : get the "id" studentExam.
     *
     * @param id the id of the studentExamDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the studentExamDTO, or with status 404 (Not Found)
     */
    @GetMapping("/student-exams/{id}")
    @Timed
    public ResponseEntity<StudentExamDTO> getStudentExam(@PathVariable Long id) {
        log.debug("REST request to get StudentExam : {}", id);
        StudentExamDTO studentExamDTO = studentExamService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(studentExamDTO));
    }

    /**
     * DELETE  /student-exams/:id : delete the "id" studentExam.
     *
     * @param id the id of the studentExamDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/student-exams/{id}")
    @Timed
    public ResponseEntity<Void> deleteStudentExam(@PathVariable Long id) {
        log.debug("REST request to delete StudentExam : {}", id);
        studentExamService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/student-exams?query=:query : search for the studentExam corresponding
     * to the query.
     *
     * @param query    the query of the studentExam search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/student-exams")
    @Timed
    public ResponseEntity<List<StudentExamDTO>> searchStudentExams(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of StudentExams for query {}", query);
        Page<StudentExamDTO> page = studentExamService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/student-exams");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/exam-application-data")
    public ResponseEntity<ExamApplicationPageData> examApplicationPageData() {
        ExamApplicationPageData data = studentExamService.getApplicationPageData();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PutMapping("/apply-for-exam")
    public ResponseEntity<ExamApplicationPageData> applyForExam(@RequestParam(name = "exam") Long examId) {
        studentExamService.applyForExam(examId);
        return examApplicationPageData();
    }

    @PutMapping("/cancel-exam-application")
    public ResponseEntity<ExamApplicationPageData> cancelExamApplication(@RequestParam(name = "exam") Long examId) {
        studentExamService.cancelExamApplication(examId);
        return examApplicationPageData();
    }
}
