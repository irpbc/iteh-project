package irpbc.iteh.web.rest;

import irpbc.iteh.ItehProjectApp;

import irpbc.iteh.domain.StudentCommitment;
import irpbc.iteh.domain.CourseEnrollment;
import irpbc.iteh.domain.Commitment;
import irpbc.iteh.repository.StudentCommitmentRepository;
import irpbc.iteh.service.StudentCommitmentService;
import irpbc.iteh.repository.search.StudentCommitmentSearchRepository;
import irpbc.iteh.service.dto.StudentCommitmentDTO;
import irpbc.iteh.service.mapper.StudentCommitmentMapper;
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
import java.math.BigDecimal;
import java.util.List;

import static irpbc.iteh.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the StudentCommitmentResource REST controller.
 *
 * @see StudentCommitmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItehProjectApp.class)
public class StudentCommitmentResourceIntTest {

    private static final BigDecimal DEFAULT_POINTS = new BigDecimal(1);
    private static final BigDecimal UPDATED_POINTS = new BigDecimal(2);

    @Autowired
    private StudentCommitmentRepository studentCommitmentRepository;

    @Autowired
    private StudentCommitmentMapper studentCommitmentMapper;

    @Autowired
    private StudentCommitmentService studentCommitmentService;

    @Autowired
    private StudentCommitmentSearchRepository studentCommitmentSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStudentCommitmentMockMvc;

    private StudentCommitment studentCommitment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudentCommitmentResource studentCommitmentResource = new StudentCommitmentResource(studentCommitmentService);
        this.restStudentCommitmentMockMvc = MockMvcBuilders.standaloneSetup(studentCommitmentResource)
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
    public static StudentCommitment createEntity(EntityManager em) {
        StudentCommitment studentCommitment = new StudentCommitment()
            .points(DEFAULT_POINTS);
        // Add required entity
        CourseEnrollment enrollment = CourseEnrollmentResourceIntTest.createEntity(em);
        em.persist(enrollment);
        em.flush();
        studentCommitment.setEnrollment(enrollment);
        // Add required entity
        Commitment commitment = CommitmentResourceIntTest.createEntity(em);
        em.persist(commitment);
        em.flush();
        studentCommitment.setCommitment(commitment);
        return studentCommitment;
    }

    @Before
    public void initTest() {
        studentCommitmentSearchRepository.deleteAll();
        studentCommitment = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudentCommitment() throws Exception {
        int databaseSizeBeforeCreate = studentCommitmentRepository.findAll().size();

        // Create the StudentCommitment
        StudentCommitmentDTO studentCommitmentDTO = studentCommitmentMapper.toDto(studentCommitment);
        restStudentCommitmentMockMvc.perform(post("/api/student-commitments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentCommitmentDTO)))
            .andExpect(status().isCreated());

        // Validate the StudentCommitment in the database
        List<StudentCommitment> studentCommitmentList = studentCommitmentRepository.findAll();
        assertThat(studentCommitmentList).hasSize(databaseSizeBeforeCreate + 1);
        StudentCommitment testStudentCommitment = studentCommitmentList.get(studentCommitmentList.size() - 1);
        assertThat(testStudentCommitment.getPoints()).isEqualTo(DEFAULT_POINTS);

        // Validate the StudentCommitment in Elasticsearch
        StudentCommitment studentCommitmentEs = studentCommitmentSearchRepository.findOne(testStudentCommitment.getId());
        assertThat(studentCommitmentEs).isEqualToIgnoringGivenFields(testStudentCommitment);
    }

    @Test
    @Transactional
    public void createStudentCommitmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentCommitmentRepository.findAll().size();

        // Create the StudentCommitment with an existing ID
        studentCommitment.setId(1L);
        StudentCommitmentDTO studentCommitmentDTO = studentCommitmentMapper.toDto(studentCommitment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentCommitmentMockMvc.perform(post("/api/student-commitments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentCommitmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudentCommitment in the database
        List<StudentCommitment> studentCommitmentList = studentCommitmentRepository.findAll();
        assertThat(studentCommitmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllStudentCommitments() throws Exception {
        // Initialize the database
        studentCommitmentRepository.saveAndFlush(studentCommitment);

        // Get all the studentCommitmentList
        restStudentCommitmentMockMvc.perform(get("/api/student-commitments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentCommitment.getId().intValue())))
            .andExpect(jsonPath("$.[*].points").value(hasItem(DEFAULT_POINTS.intValue())));
    }

    @Test
    @Transactional
    public void getStudentCommitment() throws Exception {
        // Initialize the database
        studentCommitmentRepository.saveAndFlush(studentCommitment);

        // Get the studentCommitment
        restStudentCommitmentMockMvc.perform(get("/api/student-commitments/{id}", studentCommitment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(studentCommitment.getId().intValue()))
            .andExpect(jsonPath("$.points").value(DEFAULT_POINTS.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingStudentCommitment() throws Exception {
        // Get the studentCommitment
        restStudentCommitmentMockMvc.perform(get("/api/student-commitments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudentCommitment() throws Exception {
        // Initialize the database
        studentCommitmentRepository.saveAndFlush(studentCommitment);
        studentCommitmentSearchRepository.save(studentCommitment);
        int databaseSizeBeforeUpdate = studentCommitmentRepository.findAll().size();

        // Update the studentCommitment
        StudentCommitment updatedStudentCommitment = studentCommitmentRepository.findOne(studentCommitment.getId());
        // Disconnect from session so that the updates on updatedStudentCommitment are not directly saved in db
        em.detach(updatedStudentCommitment);
        updatedStudentCommitment
            .points(UPDATED_POINTS);
        StudentCommitmentDTO studentCommitmentDTO = studentCommitmentMapper.toDto(updatedStudentCommitment);

        restStudentCommitmentMockMvc.perform(put("/api/student-commitments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentCommitmentDTO)))
            .andExpect(status().isOk());

        // Validate the StudentCommitment in the database
        List<StudentCommitment> studentCommitmentList = studentCommitmentRepository.findAll();
        assertThat(studentCommitmentList).hasSize(databaseSizeBeforeUpdate);
        StudentCommitment testStudentCommitment = studentCommitmentList.get(studentCommitmentList.size() - 1);
        assertThat(testStudentCommitment.getPoints()).isEqualTo(UPDATED_POINTS);

        // Validate the StudentCommitment in Elasticsearch
        StudentCommitment studentCommitmentEs = studentCommitmentSearchRepository.findOne(testStudentCommitment.getId());
        assertThat(studentCommitmentEs).isEqualToIgnoringGivenFields(testStudentCommitment);
    }

    @Test
    @Transactional
    public void updateNonExistingStudentCommitment() throws Exception {
        int databaseSizeBeforeUpdate = studentCommitmentRepository.findAll().size();

        // Create the StudentCommitment
        StudentCommitmentDTO studentCommitmentDTO = studentCommitmentMapper.toDto(studentCommitment);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStudentCommitmentMockMvc.perform(put("/api/student-commitments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentCommitmentDTO)))
            .andExpect(status().isCreated());

        // Validate the StudentCommitment in the database
        List<StudentCommitment> studentCommitmentList = studentCommitmentRepository.findAll();
        assertThat(studentCommitmentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteStudentCommitment() throws Exception {
        // Initialize the database
        studentCommitmentRepository.saveAndFlush(studentCommitment);
        studentCommitmentSearchRepository.save(studentCommitment);
        int databaseSizeBeforeDelete = studentCommitmentRepository.findAll().size();

        // Get the studentCommitment
        restStudentCommitmentMockMvc.perform(delete("/api/student-commitments/{id}", studentCommitment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean studentCommitmentExistsInEs = studentCommitmentSearchRepository.exists(studentCommitment.getId());
        assertThat(studentCommitmentExistsInEs).isFalse();

        // Validate the database is empty
        List<StudentCommitment> studentCommitmentList = studentCommitmentRepository.findAll();
        assertThat(studentCommitmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchStudentCommitment() throws Exception {
        // Initialize the database
        studentCommitmentRepository.saveAndFlush(studentCommitment);
        studentCommitmentSearchRepository.save(studentCommitment);

        // Search the studentCommitment
        restStudentCommitmentMockMvc.perform(get("/api/_search/student-commitments?query=id:" + studentCommitment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentCommitment.getId().intValue())))
            .andExpect(jsonPath("$.[*].points").value(hasItem(DEFAULT_POINTS.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentCommitment.class);
        StudentCommitment studentCommitment1 = new StudentCommitment();
        studentCommitment1.setId(1L);
        StudentCommitment studentCommitment2 = new StudentCommitment();
        studentCommitment2.setId(studentCommitment1.getId());
        assertThat(studentCommitment1).isEqualTo(studentCommitment2);
        studentCommitment2.setId(2L);
        assertThat(studentCommitment1).isNotEqualTo(studentCommitment2);
        studentCommitment1.setId(null);
        assertThat(studentCommitment1).isNotEqualTo(studentCommitment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentCommitmentDTO.class);
        StudentCommitmentDTO studentCommitmentDTO1 = new StudentCommitmentDTO();
        studentCommitmentDTO1.setId(1L);
        StudentCommitmentDTO studentCommitmentDTO2 = new StudentCommitmentDTO();
        assertThat(studentCommitmentDTO1).isNotEqualTo(studentCommitmentDTO2);
        studentCommitmentDTO2.setId(studentCommitmentDTO1.getId());
        assertThat(studentCommitmentDTO1).isEqualTo(studentCommitmentDTO2);
        studentCommitmentDTO2.setId(2L);
        assertThat(studentCommitmentDTO1).isNotEqualTo(studentCommitmentDTO2);
        studentCommitmentDTO1.setId(null);
        assertThat(studentCommitmentDTO1).isNotEqualTo(studentCommitmentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(studentCommitmentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(studentCommitmentMapper.fromId(null)).isNull();
    }
}
