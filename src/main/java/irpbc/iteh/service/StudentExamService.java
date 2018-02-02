package irpbc.iteh.service;

import irpbc.iteh.domain.Exam;
import irpbc.iteh.domain.ExamPeriod;
import irpbc.iteh.domain.StudentExam;
import irpbc.iteh.repository.*;
import irpbc.iteh.repository.search.StudentExamSearchRepository;
import irpbc.iteh.security.SecurityUtils;
import irpbc.iteh.service.dto.ExamApplicationPageData;
import irpbc.iteh.service.dto.ExamDTO;
import irpbc.iteh.service.dto.ExamPeriodDTO;
import irpbc.iteh.service.dto.StudentExamDTO;
import irpbc.iteh.service.mapper.ExamMapper;
import irpbc.iteh.service.mapper.ExamPeriodMapper;
import irpbc.iteh.service.mapper.StudentExamMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing StudentExam.
 */
@Service
@Transactional
public class StudentExamService {

    private final Logger log = LoggerFactory.getLogger(StudentExamService.class);

    private final StudentExamRepository studentExamRepository;
    private final StudentExamMapper studentExamMapper;
    private final StudentExamSearchRepository studentExamSearchRepository;
    private final ExamPeriodRepository examPeriodRepository;
    private final ExamPeriodMapper examPeriodMapper;
    private final ExamMapper examMapper;
    private final ExamRepository examRepository;
    private final UserRepository userRepository;
    private final CourseEnrollmentRepository courseEnrollmentRepository;

    public StudentExamService(StudentExamRepository studentExamRepository,
                              StudentExamMapper studentExamMapper,
                              StudentExamSearchRepository studentExamSearchRepository,
                              ExamPeriodRepository examPeriodRepository,
                              ExamPeriodMapper examPeriodMapper,
                              ExamMapper examMapper,
                              ExamRepository examRepository,
                              UserRepository userRepository,
                              CourseEnrollmentRepository courseEnrollmentRepository) {
        this.studentExamRepository = studentExamRepository;
        this.studentExamMapper = studentExamMapper;
        this.studentExamSearchRepository = studentExamSearchRepository;
        this.examPeriodRepository = examPeriodRepository;
        this.examPeriodMapper = examPeriodMapper;
        this.examMapper = examMapper;
        this.examRepository = examRepository;
        this.userRepository = userRepository;
        this.courseEnrollmentRepository = courseEnrollmentRepository;
    }

    /**
     * Save a studentExam.
     *
     * @param studentExamDTO the entity to save
     * @return the persisted entity
     */
    public StudentExamDTO save(StudentExamDTO studentExamDTO) {
        log.debug("Request to save StudentExam : {}", studentExamDTO);
        StudentExam studentExam = studentExamMapper.toEntity(studentExamDTO);
        studentExam = studentExamRepository.save(studentExam);
        StudentExamDTO result = studentExamMapper.toDto(studentExam);
        studentExamSearchRepository.save(studentExam);
        return result;
    }

    /**
     * Get all the studentExams.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<StudentExamDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StudentExams");
        return studentExamRepository.findAll(pageable)
            .map(studentExamMapper::toDto);
    }

    /**
     * Get one studentExam by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public StudentExamDTO findOne(Long id) {
        log.debug("Request to get StudentExam : {}", id);
        StudentExam studentExam = studentExamRepository.findOne(id);
        return studentExamMapper.toDto(studentExam);
    }

    /**
     * Delete the studentExam by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete StudentExam : {}", id);
        studentExamRepository.delete(id);
        studentExamSearchRepository.delete(id);
    }

    /**
     * Search for the studentExam corresponding to the query.
     *
     * @param query    the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<StudentExamDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of StudentExams for query {}", query);
        Page<StudentExam> result = studentExamSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(studentExamMapper::toDto);
    }

    @Transactional(readOnly = true)
    public ExamApplicationPageData getApplicationPageData() {
        Long studentId = SecurityUtils.getCurrentUserId();

        Page<ExamPeriod> page = examPeriodRepository.findNextPeriod(LocalDate.now(), new PageRequest(0, 1));
        ExamPeriodDTO nextPeriodDTO = examPeriodMapper.toDto(page.getContent().get(0));

        List<Exam> exams = studentExamRepository.findNextExamsForStudent(studentId, nextPeriodDTO.getId());
        List<ExamDTO> examDTOS = examMapper.toDto(exams);

        List<Exam> applied = studentExamRepository.findAppliedExams(studentId, nextPeriodDTO.getId());
        List<ExamDTO> appliedDTOs = examMapper.toDto(applied);

        return new ExamApplicationPageData(nextPeriodDTO, examDTOS, appliedDTOs);
    }

    public void applyForExam(Long examId) {
        Long studentId = SecurityUtils.getCurrentUserId();

        Exam exam = examRepository.findOne(examId);

        StudentExam studentExam = new StudentExam()
            .exam(exam)
            .student(userRepository.findOne(studentId));

        studentExamRepository.save(studentExam);
    }

    public void cancelExamApplication(Long examId) {
        Long studentId = SecurityUtils.getCurrentUserId();
        studentExamRepository.deleteByExam_IdAndStudent_Id(examId, studentId);
    }
}
