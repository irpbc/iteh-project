package irpbc.iteh.web.rest;

import irpbc.iteh.ItehProjectApp;

import irpbc.iteh.domain.Semester;
import irpbc.iteh.domain.SchoolYear;
import irpbc.iteh.repository.SemesterRepository;
import irpbc.iteh.service.SemesterService;
import irpbc.iteh.repository.search.SemesterSearchRepository;
import irpbc.iteh.service.dto.SemesterDTO;
import irpbc.iteh.service.mapper.SemesterMapper;
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
 * Test class for the SemesterResource REST controller.
 *
 * @see SemesterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItehProjectApp.class)
public class SemesterResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private SemesterMapper semesterMapper;

    @Autowired
    private SemesterService semesterService;

    @Autowired
    private SemesterSearchRepository semesterSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSemesterMockMvc;

    private Semester semester;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SemesterResource semesterResource = new SemesterResource(semesterService);
        this.restSemesterMockMvc = MockMvcBuilders.standaloneSetup(semesterResource)
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
    public static Semester createEntity(EntityManager em) {
        Semester semester = new Semester()
            .name(DEFAULT_NAME);
        // Add required entity
        SchoolYear year = SchoolYearResourceIntTest.createEntity(em);
        em.persist(year);
        em.flush();
        semester.setYear(year);
        return semester;
    }

    @Before
    public void initTest() {
        semesterSearchRepository.deleteAll();
        semester = createEntity(em);
    }

    @Test
    @Transactional
    public void createSemester() throws Exception {
        int databaseSizeBeforeCreate = semesterRepository.findAll().size();

        // Create the Semester
        SemesterDTO semesterDTO = semesterMapper.toDto(semester);
        restSemesterMockMvc.perform(post("/api/semesters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(semesterDTO)))
            .andExpect(status().isCreated());

        // Validate the Semester in the database
        List<Semester> semesterList = semesterRepository.findAll();
        assertThat(semesterList).hasSize(databaseSizeBeforeCreate + 1);
        Semester testSemester = semesterList.get(semesterList.size() - 1);
        assertThat(testSemester.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the Semester in Elasticsearch
        Semester semesterEs = semesterSearchRepository.findOne(testSemester.getId());
        assertThat(semesterEs).isEqualToIgnoringGivenFields(testSemester);
    }

    @Test
    @Transactional
    public void createSemesterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = semesterRepository.findAll().size();

        // Create the Semester with an existing ID
        semester.setId(1L);
        SemesterDTO semesterDTO = semesterMapper.toDto(semester);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSemesterMockMvc.perform(post("/api/semesters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(semesterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Semester in the database
        List<Semester> semesterList = semesterRepository.findAll();
        assertThat(semesterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = semesterRepository.findAll().size();
        // set the field null
        semester.setName(null);

        // Create the Semester, which fails.
        SemesterDTO semesterDTO = semesterMapper.toDto(semester);

        restSemesterMockMvc.perform(post("/api/semesters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(semesterDTO)))
            .andExpect(status().isBadRequest());

        List<Semester> semesterList = semesterRepository.findAll();
        assertThat(semesterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSemesters() throws Exception {
        // Initialize the database
        semesterRepository.saveAndFlush(semester);

        // Get all the semesterList
        restSemesterMockMvc.perform(get("/api/semesters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(semester.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getSemester() throws Exception {
        // Initialize the database
        semesterRepository.saveAndFlush(semester);

        // Get the semester
        restSemesterMockMvc.perform(get("/api/semesters/{id}", semester.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(semester.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSemester() throws Exception {
        // Get the semester
        restSemesterMockMvc.perform(get("/api/semesters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSemester() throws Exception {
        // Initialize the database
        semesterRepository.saveAndFlush(semester);
        semesterSearchRepository.save(semester);
        int databaseSizeBeforeUpdate = semesterRepository.findAll().size();

        // Update the semester
        Semester updatedSemester = semesterRepository.findOne(semester.getId());
        // Disconnect from session so that the updates on updatedSemester are not directly saved in db
        em.detach(updatedSemester);
        updatedSemester
            .name(UPDATED_NAME);
        SemesterDTO semesterDTO = semesterMapper.toDto(updatedSemester);

        restSemesterMockMvc.perform(put("/api/semesters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(semesterDTO)))
            .andExpect(status().isOk());

        // Validate the Semester in the database
        List<Semester> semesterList = semesterRepository.findAll();
        assertThat(semesterList).hasSize(databaseSizeBeforeUpdate);
        Semester testSemester = semesterList.get(semesterList.size() - 1);
        assertThat(testSemester.getName()).isEqualTo(UPDATED_NAME);

        // Validate the Semester in Elasticsearch
        Semester semesterEs = semesterSearchRepository.findOne(testSemester.getId());
        assertThat(semesterEs).isEqualToIgnoringGivenFields(testSemester);
    }

    @Test
    @Transactional
    public void updateNonExistingSemester() throws Exception {
        int databaseSizeBeforeUpdate = semesterRepository.findAll().size();

        // Create the Semester
        SemesterDTO semesterDTO = semesterMapper.toDto(semester);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSemesterMockMvc.perform(put("/api/semesters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(semesterDTO)))
            .andExpect(status().isCreated());

        // Validate the Semester in the database
        List<Semester> semesterList = semesterRepository.findAll();
        assertThat(semesterList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSemester() throws Exception {
        // Initialize the database
        semesterRepository.saveAndFlush(semester);
        semesterSearchRepository.save(semester);
        int databaseSizeBeforeDelete = semesterRepository.findAll().size();

        // Get the semester
        restSemesterMockMvc.perform(delete("/api/semesters/{id}", semester.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean semesterExistsInEs = semesterSearchRepository.exists(semester.getId());
        assertThat(semesterExistsInEs).isFalse();

        // Validate the database is empty
        List<Semester> semesterList = semesterRepository.findAll();
        assertThat(semesterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSemester() throws Exception {
        // Initialize the database
        semesterRepository.saveAndFlush(semester);
        semesterSearchRepository.save(semester);

        // Search the semester
        restSemesterMockMvc.perform(get("/api/_search/semesters?query=id:" + semester.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(semester.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Semester.class);
        Semester semester1 = new Semester();
        semester1.setId(1L);
        Semester semester2 = new Semester();
        semester2.setId(semester1.getId());
        assertThat(semester1).isEqualTo(semester2);
        semester2.setId(2L);
        assertThat(semester1).isNotEqualTo(semester2);
        semester1.setId(null);
        assertThat(semester1).isNotEqualTo(semester2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SemesterDTO.class);
        SemesterDTO semesterDTO1 = new SemesterDTO();
        semesterDTO1.setId(1L);
        SemesterDTO semesterDTO2 = new SemesterDTO();
        assertThat(semesterDTO1).isNotEqualTo(semesterDTO2);
        semesterDTO2.setId(semesterDTO1.getId());
        assertThat(semesterDTO1).isEqualTo(semesterDTO2);
        semesterDTO2.setId(2L);
        assertThat(semesterDTO1).isNotEqualTo(semesterDTO2);
        semesterDTO1.setId(null);
        assertThat(semesterDTO1).isNotEqualTo(semesterDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(semesterMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(semesterMapper.fromId(null)).isNull();
    }
}
