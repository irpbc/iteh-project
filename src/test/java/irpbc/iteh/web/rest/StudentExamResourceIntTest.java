package irpbc.iteh.web.rest;

import irpbc.iteh.ItehProjectApp;

import irpbc.iteh.domain.StudentExam;
import irpbc.iteh.domain.User;
import irpbc.iteh.domain.Exam;
import irpbc.iteh.repository.StudentExamRepository;
import irpbc.iteh.service.StudentExamService;
import irpbc.iteh.repository.search.StudentExamSearchRepository;
import irpbc.iteh.service.dto.StudentExamDTO;
import irpbc.iteh.service.mapper.StudentExamMapper;
import irpbc.iteh.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static irpbc.iteh.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the StudentExamResource REST controller.
 *
 * @see StudentExamResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItehProjectApp.class)
public class StudentExamResourceIntTest {

    private static final Boolean DEFAULT_ATTENDED = false;
    private static final Boolean UPDATED_ATTENDED = true;

    private static final Integer DEFAULT_GRADE = 6;
    private static final Integer UPDATED_GRADE = 7;

    @Autowired
    private StudentExamRepository studentExamRepository;

    @Autowired
    private StudentExamMapper studentExamMapper;

    @Autowired
    private StudentExamService studentExamService;

    @Autowired
    private StudentExamSearchRepository studentExamSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStudentExamMockMvc;

    private StudentExam studentExam;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudentExamResource studentExamResource = new StudentExamResource(studentExamService);
        this.restStudentExamMockMvc = MockMvcBuilders.standaloneSetup(studentExamResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudentExam createEntity(EntityManager em) {
        StudentExam studentExam = new StudentExam()
            .attended(DEFAULT_ATTENDED)
            .grade(DEFAULT_GRADE);
        // Add required entity
        User student = UserResourceIntTest.createEntity(em);
        em.persist(student);
        em.flush();
        studentExam.setStudent(student);
        // Add required entity
        Exam exam = ExamResourceIntTest.createEntity(em);
        em.persist(exam);
        em.flush();
        studentExam.setExam(exam);
        return studentExam;
    }

    @Before
    public void initTest() {
        studentExamSearchRepository.deleteAll();
        studentExam = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudentExam() throws Exception {
        int databaseSizeBeforeCreate = studentExamRepository.findAll().size();

        // Create the StudentExam
        StudentExamDTO studentExamDTO = studentExamMapper.toDto(studentExam);
        restStudentExamMockMvc.perform(post("/api/student-exams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentExamDTO)))
            .andExpect(status().isCreated());

        // Validate the StudentExam in the database
        List<StudentExam> studentExamList = studentExamRepository.findAll();
        assertThat(studentExamList).hasSize(databaseSizeBeforeCreate + 1);
        StudentExam testStudentExam = studentExamList.get(studentExamList.size() - 1);
        assertThat(testStudentExam.isAttended()).isEqualTo(DEFAULT_ATTENDED);
        assertThat(testStudentExam.getGrade()).isEqualTo(DEFAULT_GRADE);

        // Validate the StudentExam in Elasticsearch
        StudentExam studentExamEs = studentExamSearchRepository.findOne(testStudentExam.getId());
        assertThat(studentExamEs).isEqualToIgnoringGivenFields(testStudentExam);
    }

    @Test
    @Transactional
    public void createStudentExamWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentExamRepository.findAll().size();

        // Create the StudentExam with an existing ID
        studentExam.setId(1L);
        StudentExamDTO studentExamDTO = studentExamMapper.toDto(studentExam);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentExamMockMvc.perform(post("/api/student-exams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentExamDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudentExam in the database
        List<StudentExam> studentExamList = studentExamRepository.findAll();
        assertThat(studentExamList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllStudentExams() throws Exception {
        // Initialize the database
        studentExamRepository.saveAndFlush(studentExam);

        // Get all the studentExamList
        restStudentExamMockMvc.perform(get("/api/student-exams?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentExam.getId().intValue())))
            .andExpect(jsonPath("$.[*].attended").value(hasItem(DEFAULT_ATTENDED.booleanValue())))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE)));
    }

    @Test
    @Transactional
    public void getStudentExam() throws Exception {
        // Initialize the database
        studentExamRepository.saveAndFlush(studentExam);

        // Get the studentExam
        restStudentExamMockMvc.perform(get("/api/student-exams/{id}", studentExam.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(studentExam.getId().intValue()))
            .andExpect(jsonPath("$.attended").value(DEFAULT_ATTENDED.booleanValue()))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE));
    }

    @Test
    @Transactional
    public void getNonExistingStudentExam() throws Exception {
        // Get the studentExam
        restStudentExamMockMvc.perform(get("/api/student-exams/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudentExam() throws Exception {
        // Initialize the database
        studentExamRepository.saveAndFlush(studentExam);
        studentExamSearchRepository.save(studentExam);
        int databaseSizeBeforeUpdate = studentExamRepository.findAll().size();

        // Update the studentExam
        StudentExam updatedStudentExam = studentExamRepository.findOne(studentExam.getId());
        // Disconnect from session so that the updates on updatedStudentExam are not directly saved in db
        em.detach(updatedStudentExam);
        updatedStudentExam
            .attended(UPDATED_ATTENDED)
            .grade(UPDATED_GRADE);
        StudentExamDTO studentExamDTO = studentExamMapper.toDto(updatedStudentExam);

        restStudentExamMockMvc.perform(put("/api/student-exams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentExamDTO)))
            .andExpect(status().isOk());

        // Validate the StudentExam in the database
        List<StudentExam> studentExamList = studentExamRepository.findAll();
        assertThat(studentExamList).hasSize(databaseSizeBeforeUpdate);
        StudentExam testStudentExam = studentExamList.get(studentExamList.size() - 1);
        assertThat(testStudentExam.isAttended()).isEqualTo(UPDATED_ATTENDED);
        assertThat(testStudentExam.getGrade()).isEqualTo(UPDATED_GRADE);

        // Validate the StudentExam in Elasticsearch
        StudentExam studentExamEs = studentExamSearchRepository.findOne(testStudentExam.getId());
        assertThat(studentExamEs).isEqualToIgnoringGivenFields(testStudentExam);
    }

    @Test
    @Transactional
    public void updateNonExistingStudentExam() throws Exception {
        int databaseSizeBeforeUpdate = studentExamRepository.findAll().size();

        // Create the StudentExam
        StudentExamDTO studentExamDTO = studentExamMapper.toDto(studentExam);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStudentExamMockMvc.perform(put("/api/student-exams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentExamDTO)))
            .andExpect(status().isCreated());

        // Validate the StudentExam in the database
        List<StudentExam> studentExamList = studentExamRepository.findAll();
        assertThat(studentExamList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteStudentExam() throws Exception {
        // Initialize the database
        studentExamRepository.saveAndFlush(studentExam);
        studentExamSearchRepository.save(studentExam);
        int databaseSizeBeforeDelete = studentExamRepository.findAll().size();

        // Get the studentExam
        restStudentExamMockMvc.perform(delete("/api/student-exams/{id}", studentExam.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean studentExamExistsInEs = studentExamSearchRepository.exists(studentExam.getId());
        assertThat(studentExamExistsInEs).isFalse();

        // Validate the database is empty
        List<StudentExam> studentExamList = studentExamRepository.findAll();
        assertThat(studentExamList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchStudentExam() throws Exception {
        // Initialize the database
        studentExamRepository.saveAndFlush(studentExam);
        studentExamSearchRepository.save(studentExam);

        // Search the studentExam
        restStudentExamMockMvc.perform(get("/api/_search/student-exams?query=id:" + studentExam.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentExam.getId().intValue())))
            .andExpect(jsonPath("$.[*].attended").value(hasItem(DEFAULT_ATTENDED.booleanValue())))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentExam.class);
        StudentExam studentExam1 = new StudentExam();
        studentExam1.setId(1L);
        StudentExam studentExam2 = new StudentExam();
        studentExam2.setId(studentExam1.getId());
        assertThat(studentExam1).isEqualTo(studentExam2);
        studentExam2.setId(2L);
        assertThat(studentExam1).isNotEqualTo(studentExam2);
        studentExam1.setId(null);
        assertThat(studentExam1).isNotEqualTo(studentExam2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentExamDTO.class);
        StudentExamDTO studentExamDTO1 = new StudentExamDTO();
        studentExamDTO1.setId(1L);
        StudentExamDTO studentExamDTO2 = new StudentExamDTO();
        assertThat(studentExamDTO1).isNotEqualTo(studentExamDTO2);
        studentExamDTO2.setId(studentExamDTO1.getId());
        assertThat(studentExamDTO1).isEqualTo(studentExamDTO2);
        studentExamDTO2.setId(2L);
        assertThat(studentExamDTO1).isNotEqualTo(studentExamDTO2);
        studentExamDTO1.setId(null);
        assertThat(studentExamDTO1).isNotEqualTo(studentExamDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(studentExamMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(studentExamMapper.fromId(null)).isNull();
    }
}
