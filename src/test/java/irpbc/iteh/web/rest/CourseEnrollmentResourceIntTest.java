package irpbc.iteh.web.rest;

import irpbc.iteh.ItehProjectApp;

import irpbc.iteh.domain.CourseEnrollment;
import irpbc.iteh.domain.SchoolYearEnrollment;
import irpbc.iteh.domain.Course;
import irpbc.iteh.repository.CourseEnrollmentRepository;
import irpbc.iteh.service.CourseEnrollmentService;
import irpbc.iteh.repository.search.CourseEnrollmentSearchRepository;
import irpbc.iteh.service.dto.CourseEnrollmentDTO;
import irpbc.iteh.service.mapper.CourseEnrollmentMapper;
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
 * Test class for the CourseEnrollmentResource REST controller.
 *
 * @see CourseEnrollmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItehProjectApp.class)
public class CourseEnrollmentResourceIntTest {

    private static final BigDecimal DEFAULT_TOTAL_POINTS = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_POINTS = new BigDecimal(2);

    private static final Integer DEFAULT_GRADE = 6;
    private static final Integer UPDATED_GRADE = 7;

    private static final Boolean DEFAULT_COMPLETED = false;
    private static final Boolean UPDATED_COMPLETED = true;

    @Autowired
    private CourseEnrollmentRepository courseEnrollmentRepository;

    @Autowired
    private CourseEnrollmentMapper courseEnrollmentMapper;

    @Autowired
    private CourseEnrollmentService courseEnrollmentService;

    @Autowired
    private CourseEnrollmentSearchRepository courseEnrollmentSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCourseEnrollmentMockMvc;

    private CourseEnrollment courseEnrollment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CourseEnrollmentResource courseEnrollmentResource = new CourseEnrollmentResource(courseEnrollmentService);
        this.restCourseEnrollmentMockMvc = MockMvcBuilders.standaloneSetup(courseEnrollmentResource)
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
    public static CourseEnrollment createEntity(EntityManager em) {
        CourseEnrollment courseEnrollment = new CourseEnrollment()
            .totalPoints(DEFAULT_TOTAL_POINTS)
            .grade(DEFAULT_GRADE)
            .completed(DEFAULT_COMPLETED);
        // Add required entity
        SchoolYearEnrollment yearEnrollment = SchoolYearEnrollmentResourceIntTest.createEntity(em);
        em.persist(yearEnrollment);
        em.flush();
        courseEnrollment.setYearEnrollment(yearEnrollment);
        // Add required entity
        Course course = CourseResourceIntTest.createEntity(em);
        em.persist(course);
        em.flush();
        courseEnrollment.setCourse(course);
        return courseEnrollment;
    }

    @Before
    public void initTest() {
        courseEnrollmentSearchRepository.deleteAll();
        courseEnrollment = createEntity(em);
    }

    @Test
    @Transactional
    public void createCourseEnrollment() throws Exception {
        int databaseSizeBeforeCreate = courseEnrollmentRepository.findAll().size();

        // Create the CourseEnrollment
        CourseEnrollmentDTO courseEnrollmentDTO = courseEnrollmentMapper.toDto(courseEnrollment);
        restCourseEnrollmentMockMvc.perform(post("/api/course-enrollments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseEnrollmentDTO)))
            .andExpect(status().isCreated());

        // Validate the CourseEnrollment in the database
        List<CourseEnrollment> courseEnrollmentList = courseEnrollmentRepository.findAll();
        assertThat(courseEnrollmentList).hasSize(databaseSizeBeforeCreate + 1);
        CourseEnrollment testCourseEnrollment = courseEnrollmentList.get(courseEnrollmentList.size() - 1);
        assertThat(testCourseEnrollment.getTotalPoints()).isEqualTo(DEFAULT_TOTAL_POINTS);
        assertThat(testCourseEnrollment.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testCourseEnrollment.isCompleted()).isEqualTo(DEFAULT_COMPLETED);

        // Validate the CourseEnrollment in Elasticsearch
        CourseEnrollment courseEnrollmentEs = courseEnrollmentSearchRepository.findOne(testCourseEnrollment.getId());
        assertThat(courseEnrollmentEs).isEqualToIgnoringGivenFields(testCourseEnrollment);
    }

    @Test
    @Transactional
    public void createCourseEnrollmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = courseEnrollmentRepository.findAll().size();

        // Create the CourseEnrollment with an existing ID
        courseEnrollment.setId(1L);
        CourseEnrollmentDTO courseEnrollmentDTO = courseEnrollmentMapper.toDto(courseEnrollment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourseEnrollmentMockMvc.perform(post("/api/course-enrollments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseEnrollmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CourseEnrollment in the database
        List<CourseEnrollment> courseEnrollmentList = courseEnrollmentRepository.findAll();
        assertThat(courseEnrollmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTotalPointsIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseEnrollmentRepository.findAll().size();
        // set the field null
        courseEnrollment.setTotalPoints(null);

        // Create the CourseEnrollment, which fails.
        CourseEnrollmentDTO courseEnrollmentDTO = courseEnrollmentMapper.toDto(courseEnrollment);

        restCourseEnrollmentMockMvc.perform(post("/api/course-enrollments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseEnrollmentDTO)))
            .andExpect(status().isBadRequest());

        List<CourseEnrollment> courseEnrollmentList = courseEnrollmentRepository.findAll();
        assertThat(courseEnrollmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCompletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseEnrollmentRepository.findAll().size();
        // set the field null
        courseEnrollment.setCompleted(null);

        // Create the CourseEnrollment, which fails.
        CourseEnrollmentDTO courseEnrollmentDTO = courseEnrollmentMapper.toDto(courseEnrollment);

        restCourseEnrollmentMockMvc.perform(post("/api/course-enrollments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseEnrollmentDTO)))
            .andExpect(status().isBadRequest());

        List<CourseEnrollment> courseEnrollmentList = courseEnrollmentRepository.findAll();
        assertThat(courseEnrollmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCourseEnrollments() throws Exception {
        // Initialize the database
        courseEnrollmentRepository.saveAndFlush(courseEnrollment);

        // Get all the courseEnrollmentList
        restCourseEnrollmentMockMvc.perform(get("/api/course-enrollments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseEnrollment.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalPoints").value(hasItem(DEFAULT_TOTAL_POINTS.intValue())))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE)))
            .andExpect(jsonPath("$.[*].completed").value(hasItem(DEFAULT_COMPLETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getCourseEnrollment() throws Exception {
        // Initialize the database
        courseEnrollmentRepository.saveAndFlush(courseEnrollment);

        // Get the courseEnrollment
        restCourseEnrollmentMockMvc.perform(get("/api/course-enrollments/{id}", courseEnrollment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(courseEnrollment.getId().intValue()))
            .andExpect(jsonPath("$.totalPoints").value(DEFAULT_TOTAL_POINTS.intValue()))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE))
            .andExpect(jsonPath("$.completed").value(DEFAULT_COMPLETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCourseEnrollment() throws Exception {
        // Get the courseEnrollment
        restCourseEnrollmentMockMvc.perform(get("/api/course-enrollments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCourseEnrollment() throws Exception {
        // Initialize the database
        courseEnrollmentRepository.saveAndFlush(courseEnrollment);
        courseEnrollmentSearchRepository.save(courseEnrollment);
        int databaseSizeBeforeUpdate = courseEnrollmentRepository.findAll().size();

        // Update the courseEnrollment
        CourseEnrollment updatedCourseEnrollment = courseEnrollmentRepository.findOne(courseEnrollment.getId());
        // Disconnect from session so that the updates on updatedCourseEnrollment are not directly saved in db
        em.detach(updatedCourseEnrollment);
        updatedCourseEnrollment
            .totalPoints(UPDATED_TOTAL_POINTS)
            .grade(UPDATED_GRADE)
            .completed(UPDATED_COMPLETED);
        CourseEnrollmentDTO courseEnrollmentDTO = courseEnrollmentMapper.toDto(updatedCourseEnrollment);

        restCourseEnrollmentMockMvc.perform(put("/api/course-enrollments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseEnrollmentDTO)))
            .andExpect(status().isOk());

        // Validate the CourseEnrollment in the database
        List<CourseEnrollment> courseEnrollmentList = courseEnrollmentRepository.findAll();
        assertThat(courseEnrollmentList).hasSize(databaseSizeBeforeUpdate);
        CourseEnrollment testCourseEnrollment = courseEnrollmentList.get(courseEnrollmentList.size() - 1);
        assertThat(testCourseEnrollment.getTotalPoints()).isEqualTo(UPDATED_TOTAL_POINTS);
        assertThat(testCourseEnrollment.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testCourseEnrollment.isCompleted()).isEqualTo(UPDATED_COMPLETED);

        // Validate the CourseEnrollment in Elasticsearch
        CourseEnrollment courseEnrollmentEs = courseEnrollmentSearchRepository.findOne(testCourseEnrollment.getId());
        assertThat(courseEnrollmentEs).isEqualToIgnoringGivenFields(testCourseEnrollment);
    }

    @Test
    @Transactional
    public void updateNonExistingCourseEnrollment() throws Exception {
        int databaseSizeBeforeUpdate = courseEnrollmentRepository.findAll().size();

        // Create the CourseEnrollment
        CourseEnrollmentDTO courseEnrollmentDTO = courseEnrollmentMapper.toDto(courseEnrollment);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCourseEnrollmentMockMvc.perform(put("/api/course-enrollments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseEnrollmentDTO)))
            .andExpect(status().isCreated());

        // Validate the CourseEnrollment in the database
        List<CourseEnrollment> courseEnrollmentList = courseEnrollmentRepository.findAll();
        assertThat(courseEnrollmentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCourseEnrollment() throws Exception {
        // Initialize the database
        courseEnrollmentRepository.saveAndFlush(courseEnrollment);
        courseEnrollmentSearchRepository.save(courseEnrollment);
        int databaseSizeBeforeDelete = courseEnrollmentRepository.findAll().size();

        // Get the courseEnrollment
        restCourseEnrollmentMockMvc.perform(delete("/api/course-enrollments/{id}", courseEnrollment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean courseEnrollmentExistsInEs = courseEnrollmentSearchRepository.exists(courseEnrollment.getId());
        assertThat(courseEnrollmentExistsInEs).isFalse();

        // Validate the database is empty
        List<CourseEnrollment> courseEnrollmentList = courseEnrollmentRepository.findAll();
        assertThat(courseEnrollmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCourseEnrollment() throws Exception {
        // Initialize the database
        courseEnrollmentRepository.saveAndFlush(courseEnrollment);
        courseEnrollmentSearchRepository.save(courseEnrollment);

        // Search the courseEnrollment
        restCourseEnrollmentMockMvc.perform(get("/api/_search/course-enrollments?query=id:" + courseEnrollment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseEnrollment.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalPoints").value(hasItem(DEFAULT_TOTAL_POINTS.intValue())))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE)))
            .andExpect(jsonPath("$.[*].completed").value(hasItem(DEFAULT_COMPLETED.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseEnrollment.class);
        CourseEnrollment courseEnrollment1 = new CourseEnrollment();
        courseEnrollment1.setId(1L);
        CourseEnrollment courseEnrollment2 = new CourseEnrollment();
        courseEnrollment2.setId(courseEnrollment1.getId());
        assertThat(courseEnrollment1).isEqualTo(courseEnrollment2);
        courseEnrollment2.setId(2L);
        assertThat(courseEnrollment1).isNotEqualTo(courseEnrollment2);
        courseEnrollment1.setId(null);
        assertThat(courseEnrollment1).isNotEqualTo(courseEnrollment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseEnrollmentDTO.class);
        CourseEnrollmentDTO courseEnrollmentDTO1 = new CourseEnrollmentDTO();
        courseEnrollmentDTO1.setId(1L);
        CourseEnrollmentDTO courseEnrollmentDTO2 = new CourseEnrollmentDTO();
        assertThat(courseEnrollmentDTO1).isNotEqualTo(courseEnrollmentDTO2);
        courseEnrollmentDTO2.setId(courseEnrollmentDTO1.getId());
        assertThat(courseEnrollmentDTO1).isEqualTo(courseEnrollmentDTO2);
        courseEnrollmentDTO2.setId(2L);
        assertThat(courseEnrollmentDTO1).isNotEqualTo(courseEnrollmentDTO2);
        courseEnrollmentDTO1.setId(null);
        assertThat(courseEnrollmentDTO1).isNotEqualTo(courseEnrollmentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(courseEnrollmentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(courseEnrollmentMapper.fromId(null)).isNull();
    }
}
