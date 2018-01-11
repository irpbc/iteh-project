package irpbc.iteh.web.rest;

import irpbc.iteh.ItehProjectApp;

import irpbc.iteh.domain.SchoolYearEnrollment;
import irpbc.iteh.domain.User;
import irpbc.iteh.domain.SchoolYear;
import irpbc.iteh.repository.SchoolYearEnrollmentRepository;
import irpbc.iteh.service.SchoolYearEnrollmentService;
import irpbc.iteh.repository.search.SchoolYearEnrollmentSearchRepository;
import irpbc.iteh.service.dto.SchoolYearEnrollmentDTO;
import irpbc.iteh.service.mapper.SchoolYearEnrollmentMapper;
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
 * Test class for the SchoolYearEnrollmentResource REST controller.
 *
 * @see SchoolYearEnrollmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItehProjectApp.class)
public class SchoolYearEnrollmentResourceIntTest {

    private static final BigDecimal DEFAULT_AVERAGE_GRADE = new BigDecimal(6);
    private static final BigDecimal UPDATED_AVERAGE_GRADE = new BigDecimal(7);

    private static final Integer DEFAULT_ESPB_POINTS_DECLARED = 1;
    private static final Integer UPDATED_ESPB_POINTS_DECLARED = 2;

    private static final Integer DEFAULT_ESPB_POINTS_ATTAINED = 1;
    private static final Integer UPDATED_ESPB_POINTS_ATTAINED = 2;

    @Autowired
    private SchoolYearEnrollmentRepository schoolYearEnrollmentRepository;

    @Autowired
    private SchoolYearEnrollmentMapper schoolYearEnrollmentMapper;

    @Autowired
    private SchoolYearEnrollmentService schoolYearEnrollmentService;

    @Autowired
    private SchoolYearEnrollmentSearchRepository schoolYearEnrollmentSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSchoolYearEnrollmentMockMvc;

    private SchoolYearEnrollment schoolYearEnrollment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SchoolYearEnrollmentResource schoolYearEnrollmentResource = new SchoolYearEnrollmentResource(schoolYearEnrollmentService);
        this.restSchoolYearEnrollmentMockMvc = MockMvcBuilders.standaloneSetup(schoolYearEnrollmentResource)
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
    public static SchoolYearEnrollment createEntity(EntityManager em) {
        SchoolYearEnrollment schoolYearEnrollment = new SchoolYearEnrollment()
            .averageGrade(DEFAULT_AVERAGE_GRADE)
            .espbPointsDeclared(DEFAULT_ESPB_POINTS_DECLARED)
            .espbPointsAttained(DEFAULT_ESPB_POINTS_ATTAINED);
        // Add required entity
        User student = UserResourceIntTest.createEntity(em);
        em.persist(student);
        em.flush();
        schoolYearEnrollment.setStudent(student);
        // Add required entity
        SchoolYear year = SchoolYearResourceIntTest.createEntity(em);
        em.persist(year);
        em.flush();
        schoolYearEnrollment.setYear(year);
        return schoolYearEnrollment;
    }

    @Before
    public void initTest() {
        schoolYearEnrollmentSearchRepository.deleteAll();
        schoolYearEnrollment = createEntity(em);
    }

    @Test
    @Transactional
    public void createSchoolYearEnrollment() throws Exception {
        int databaseSizeBeforeCreate = schoolYearEnrollmentRepository.findAll().size();

        // Create the SchoolYearEnrollment
        SchoolYearEnrollmentDTO schoolYearEnrollmentDTO = schoolYearEnrollmentMapper.toDto(schoolYearEnrollment);
        restSchoolYearEnrollmentMockMvc.perform(post("/api/school-year-enrollments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schoolYearEnrollmentDTO)))
            .andExpect(status().isCreated());

        // Validate the SchoolYearEnrollment in the database
        List<SchoolYearEnrollment> schoolYearEnrollmentList = schoolYearEnrollmentRepository.findAll();
        assertThat(schoolYearEnrollmentList).hasSize(databaseSizeBeforeCreate + 1);
        SchoolYearEnrollment testSchoolYearEnrollment = schoolYearEnrollmentList.get(schoolYearEnrollmentList.size() - 1);
        assertThat(testSchoolYearEnrollment.getAverageGrade()).isEqualTo(DEFAULT_AVERAGE_GRADE);
        assertThat(testSchoolYearEnrollment.getEspbPointsDeclared()).isEqualTo(DEFAULT_ESPB_POINTS_DECLARED);
        assertThat(testSchoolYearEnrollment.getEspbPointsAttained()).isEqualTo(DEFAULT_ESPB_POINTS_ATTAINED);

        // Validate the SchoolYearEnrollment in Elasticsearch
        SchoolYearEnrollment schoolYearEnrollmentEs = schoolYearEnrollmentSearchRepository.findOne(testSchoolYearEnrollment.getId());
        assertThat(schoolYearEnrollmentEs).isEqualToIgnoringGivenFields(testSchoolYearEnrollment);
    }

    @Test
    @Transactional
    public void createSchoolYearEnrollmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schoolYearEnrollmentRepository.findAll().size();

        // Create the SchoolYearEnrollment with an existing ID
        schoolYearEnrollment.setId(1L);
        SchoolYearEnrollmentDTO schoolYearEnrollmentDTO = schoolYearEnrollmentMapper.toDto(schoolYearEnrollment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchoolYearEnrollmentMockMvc.perform(post("/api/school-year-enrollments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schoolYearEnrollmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SchoolYearEnrollment in the database
        List<SchoolYearEnrollment> schoolYearEnrollmentList = schoolYearEnrollmentRepository.findAll();
        assertThat(schoolYearEnrollmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkEspbPointsDeclaredIsRequired() throws Exception {
        int databaseSizeBeforeTest = schoolYearEnrollmentRepository.findAll().size();
        // set the field null
        schoolYearEnrollment.setEspbPointsDeclared(null);

        // Create the SchoolYearEnrollment, which fails.
        SchoolYearEnrollmentDTO schoolYearEnrollmentDTO = schoolYearEnrollmentMapper.toDto(schoolYearEnrollment);

        restSchoolYearEnrollmentMockMvc.perform(post("/api/school-year-enrollments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schoolYearEnrollmentDTO)))
            .andExpect(status().isBadRequest());

        List<SchoolYearEnrollment> schoolYearEnrollmentList = schoolYearEnrollmentRepository.findAll();
        assertThat(schoolYearEnrollmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEspbPointsAttainedIsRequired() throws Exception {
        int databaseSizeBeforeTest = schoolYearEnrollmentRepository.findAll().size();
        // set the field null
        schoolYearEnrollment.setEspbPointsAttained(null);

        // Create the SchoolYearEnrollment, which fails.
        SchoolYearEnrollmentDTO schoolYearEnrollmentDTO = schoolYearEnrollmentMapper.toDto(schoolYearEnrollment);

        restSchoolYearEnrollmentMockMvc.perform(post("/api/school-year-enrollments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schoolYearEnrollmentDTO)))
            .andExpect(status().isBadRequest());

        List<SchoolYearEnrollment> schoolYearEnrollmentList = schoolYearEnrollmentRepository.findAll();
        assertThat(schoolYearEnrollmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSchoolYearEnrollments() throws Exception {
        // Initialize the database
        schoolYearEnrollmentRepository.saveAndFlush(schoolYearEnrollment);

        // Get all the schoolYearEnrollmentList
        restSchoolYearEnrollmentMockMvc.perform(get("/api/school-year-enrollments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schoolYearEnrollment.getId().intValue())))
            .andExpect(jsonPath("$.[*].averageGrade").value(hasItem(DEFAULT_AVERAGE_GRADE.intValue())))
            .andExpect(jsonPath("$.[*].espbPointsDeclared").value(hasItem(DEFAULT_ESPB_POINTS_DECLARED)))
            .andExpect(jsonPath("$.[*].espbPointsAttained").value(hasItem(DEFAULT_ESPB_POINTS_ATTAINED)));
    }

    @Test
    @Transactional
    public void getSchoolYearEnrollment() throws Exception {
        // Initialize the database
        schoolYearEnrollmentRepository.saveAndFlush(schoolYearEnrollment);

        // Get the schoolYearEnrollment
        restSchoolYearEnrollmentMockMvc.perform(get("/api/school-year-enrollments/{id}", schoolYearEnrollment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(schoolYearEnrollment.getId().intValue()))
            .andExpect(jsonPath("$.averageGrade").value(DEFAULT_AVERAGE_GRADE.intValue()))
            .andExpect(jsonPath("$.espbPointsDeclared").value(DEFAULT_ESPB_POINTS_DECLARED))
            .andExpect(jsonPath("$.espbPointsAttained").value(DEFAULT_ESPB_POINTS_ATTAINED));
    }

    @Test
    @Transactional
    public void getNonExistingSchoolYearEnrollment() throws Exception {
        // Get the schoolYearEnrollment
        restSchoolYearEnrollmentMockMvc.perform(get("/api/school-year-enrollments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchoolYearEnrollment() throws Exception {
        // Initialize the database
        schoolYearEnrollmentRepository.saveAndFlush(schoolYearEnrollment);
        schoolYearEnrollmentSearchRepository.save(schoolYearEnrollment);
        int databaseSizeBeforeUpdate = schoolYearEnrollmentRepository.findAll().size();

        // Update the schoolYearEnrollment
        SchoolYearEnrollment updatedSchoolYearEnrollment = schoolYearEnrollmentRepository.findOne(schoolYearEnrollment.getId());
        // Disconnect from session so that the updates on updatedSchoolYearEnrollment are not directly saved in db
        em.detach(updatedSchoolYearEnrollment);
        updatedSchoolYearEnrollment
            .averageGrade(UPDATED_AVERAGE_GRADE)
            .espbPointsDeclared(UPDATED_ESPB_POINTS_DECLARED)
            .espbPointsAttained(UPDATED_ESPB_POINTS_ATTAINED);
        SchoolYearEnrollmentDTO schoolYearEnrollmentDTO = schoolYearEnrollmentMapper.toDto(updatedSchoolYearEnrollment);

        restSchoolYearEnrollmentMockMvc.perform(put("/api/school-year-enrollments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schoolYearEnrollmentDTO)))
            .andExpect(status().isOk());

        // Validate the SchoolYearEnrollment in the database
        List<SchoolYearEnrollment> schoolYearEnrollmentList = schoolYearEnrollmentRepository.findAll();
        assertThat(schoolYearEnrollmentList).hasSize(databaseSizeBeforeUpdate);
        SchoolYearEnrollment testSchoolYearEnrollment = schoolYearEnrollmentList.get(schoolYearEnrollmentList.size() - 1);
        assertThat(testSchoolYearEnrollment.getAverageGrade()).isEqualTo(UPDATED_AVERAGE_GRADE);
        assertThat(testSchoolYearEnrollment.getEspbPointsDeclared()).isEqualTo(UPDATED_ESPB_POINTS_DECLARED);
        assertThat(testSchoolYearEnrollment.getEspbPointsAttained()).isEqualTo(UPDATED_ESPB_POINTS_ATTAINED);

        // Validate the SchoolYearEnrollment in Elasticsearch
        SchoolYearEnrollment schoolYearEnrollmentEs = schoolYearEnrollmentSearchRepository.findOne(testSchoolYearEnrollment.getId());
        assertThat(schoolYearEnrollmentEs).isEqualToIgnoringGivenFields(testSchoolYearEnrollment);
    }

    @Test
    @Transactional
    public void updateNonExistingSchoolYearEnrollment() throws Exception {
        int databaseSizeBeforeUpdate = schoolYearEnrollmentRepository.findAll().size();

        // Create the SchoolYearEnrollment
        SchoolYearEnrollmentDTO schoolYearEnrollmentDTO = schoolYearEnrollmentMapper.toDto(schoolYearEnrollment);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSchoolYearEnrollmentMockMvc.perform(put("/api/school-year-enrollments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schoolYearEnrollmentDTO)))
            .andExpect(status().isCreated());

        // Validate the SchoolYearEnrollment in the database
        List<SchoolYearEnrollment> schoolYearEnrollmentList = schoolYearEnrollmentRepository.findAll();
        assertThat(schoolYearEnrollmentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSchoolYearEnrollment() throws Exception {
        // Initialize the database
        schoolYearEnrollmentRepository.saveAndFlush(schoolYearEnrollment);
        schoolYearEnrollmentSearchRepository.save(schoolYearEnrollment);
        int databaseSizeBeforeDelete = schoolYearEnrollmentRepository.findAll().size();

        // Get the schoolYearEnrollment
        restSchoolYearEnrollmentMockMvc.perform(delete("/api/school-year-enrollments/{id}", schoolYearEnrollment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean schoolYearEnrollmentExistsInEs = schoolYearEnrollmentSearchRepository.exists(schoolYearEnrollment.getId());
        assertThat(schoolYearEnrollmentExistsInEs).isFalse();

        // Validate the database is empty
        List<SchoolYearEnrollment> schoolYearEnrollmentList = schoolYearEnrollmentRepository.findAll();
        assertThat(schoolYearEnrollmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSchoolYearEnrollment() throws Exception {
        // Initialize the database
        schoolYearEnrollmentRepository.saveAndFlush(schoolYearEnrollment);
        schoolYearEnrollmentSearchRepository.save(schoolYearEnrollment);

        // Search the schoolYearEnrollment
        restSchoolYearEnrollmentMockMvc.perform(get("/api/_search/school-year-enrollments?query=id:" + schoolYearEnrollment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schoolYearEnrollment.getId().intValue())))
            .andExpect(jsonPath("$.[*].averageGrade").value(hasItem(DEFAULT_AVERAGE_GRADE.intValue())))
            .andExpect(jsonPath("$.[*].espbPointsDeclared").value(hasItem(DEFAULT_ESPB_POINTS_DECLARED)))
            .andExpect(jsonPath("$.[*].espbPointsAttained").value(hasItem(DEFAULT_ESPB_POINTS_ATTAINED)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchoolYearEnrollment.class);
        SchoolYearEnrollment schoolYearEnrollment1 = new SchoolYearEnrollment();
        schoolYearEnrollment1.setId(1L);
        SchoolYearEnrollment schoolYearEnrollment2 = new SchoolYearEnrollment();
        schoolYearEnrollment2.setId(schoolYearEnrollment1.getId());
        assertThat(schoolYearEnrollment1).isEqualTo(schoolYearEnrollment2);
        schoolYearEnrollment2.setId(2L);
        assertThat(schoolYearEnrollment1).isNotEqualTo(schoolYearEnrollment2);
        schoolYearEnrollment1.setId(null);
        assertThat(schoolYearEnrollment1).isNotEqualTo(schoolYearEnrollment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchoolYearEnrollmentDTO.class);
        SchoolYearEnrollmentDTO schoolYearEnrollmentDTO1 = new SchoolYearEnrollmentDTO();
        schoolYearEnrollmentDTO1.setId(1L);
        SchoolYearEnrollmentDTO schoolYearEnrollmentDTO2 = new SchoolYearEnrollmentDTO();
        assertThat(schoolYearEnrollmentDTO1).isNotEqualTo(schoolYearEnrollmentDTO2);
        schoolYearEnrollmentDTO2.setId(schoolYearEnrollmentDTO1.getId());
        assertThat(schoolYearEnrollmentDTO1).isEqualTo(schoolYearEnrollmentDTO2);
        schoolYearEnrollmentDTO2.setId(2L);
        assertThat(schoolYearEnrollmentDTO1).isNotEqualTo(schoolYearEnrollmentDTO2);
        schoolYearEnrollmentDTO1.setId(null);
        assertThat(schoolYearEnrollmentDTO1).isNotEqualTo(schoolYearEnrollmentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(schoolYearEnrollmentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(schoolYearEnrollmentMapper.fromId(null)).isNull();
    }
}
