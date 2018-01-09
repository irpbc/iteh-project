package irpbc.iteh.web.rest;

import irpbc.iteh.ItehProjectApp;

import irpbc.iteh.domain.ExamPeriod;
import irpbc.iteh.domain.SchoolYear;
import irpbc.iteh.repository.ExamPeriodRepository;
import irpbc.iteh.service.ExamPeriodService;
import irpbc.iteh.repository.search.ExamPeriodSearchRepository;
import irpbc.iteh.service.dto.ExamPeriodDTO;
import irpbc.iteh.service.mapper.ExamPeriodMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static irpbc.iteh.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ExamPeriodResource REST controller.
 *
 * @see ExamPeriodResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItehProjectApp.class)
public class ExamPeriodResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ExamPeriodRepository examPeriodRepository;

    @Autowired
    private ExamPeriodMapper examPeriodMapper;

    @Autowired
    private ExamPeriodService examPeriodService;

    @Autowired
    private ExamPeriodSearchRepository examPeriodSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restExamPeriodMockMvc;

    private ExamPeriod examPeriod;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExamPeriodResource examPeriodResource = new ExamPeriodResource(examPeriodService);
        this.restExamPeriodMockMvc = MockMvcBuilders.standaloneSetup(examPeriodResource)
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
    public static ExamPeriod createEntity(EntityManager em) {
        ExamPeriod examPeriod = new ExamPeriod()
            .name(DEFAULT_NAME)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        // Add required entity
        SchoolYear year = SchoolYearResourceIntTest.createEntity(em);
        em.persist(year);
        em.flush();
        examPeriod.setYear(year);
        return examPeriod;
    }

    @Before
    public void initTest() {
        examPeriodSearchRepository.deleteAll();
        examPeriod = createEntity(em);
    }

    @Test
    @Transactional
    public void createExamPeriod() throws Exception {
        int databaseSizeBeforeCreate = examPeriodRepository.findAll().size();

        // Create the ExamPeriod
        ExamPeriodDTO examPeriodDTO = examPeriodMapper.toDto(examPeriod);
        restExamPeriodMockMvc.perform(post("/api/exam-periods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examPeriodDTO)))
            .andExpect(status().isCreated());

        // Validate the ExamPeriod in the database
        List<ExamPeriod> examPeriodList = examPeriodRepository.findAll();
        assertThat(examPeriodList).hasSize(databaseSizeBeforeCreate + 1);
        ExamPeriod testExamPeriod = examPeriodList.get(examPeriodList.size() - 1);
        assertThat(testExamPeriod.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testExamPeriod.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testExamPeriod.getEndDate()).isEqualTo(DEFAULT_END_DATE);

        // Validate the ExamPeriod in Elasticsearch
        ExamPeriod examPeriodEs = examPeriodSearchRepository.findOne(testExamPeriod.getId());
        assertThat(examPeriodEs).isEqualToIgnoringGivenFields(testExamPeriod);
    }

    @Test
    @Transactional
    public void createExamPeriodWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = examPeriodRepository.findAll().size();

        // Create the ExamPeriod with an existing ID
        examPeriod.setId(1L);
        ExamPeriodDTO examPeriodDTO = examPeriodMapper.toDto(examPeriod);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExamPeriodMockMvc.perform(post("/api/exam-periods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examPeriodDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExamPeriod in the database
        List<ExamPeriod> examPeriodList = examPeriodRepository.findAll();
        assertThat(examPeriodList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = examPeriodRepository.findAll().size();
        // set the field null
        examPeriod.setName(null);

        // Create the ExamPeriod, which fails.
        ExamPeriodDTO examPeriodDTO = examPeriodMapper.toDto(examPeriod);

        restExamPeriodMockMvc.perform(post("/api/exam-periods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examPeriodDTO)))
            .andExpect(status().isBadRequest());

        List<ExamPeriod> examPeriodList = examPeriodRepository.findAll();
        assertThat(examPeriodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = examPeriodRepository.findAll().size();
        // set the field null
        examPeriod.setStartDate(null);

        // Create the ExamPeriod, which fails.
        ExamPeriodDTO examPeriodDTO = examPeriodMapper.toDto(examPeriod);

        restExamPeriodMockMvc.perform(post("/api/exam-periods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examPeriodDTO)))
            .andExpect(status().isBadRequest());

        List<ExamPeriod> examPeriodList = examPeriodRepository.findAll();
        assertThat(examPeriodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = examPeriodRepository.findAll().size();
        // set the field null
        examPeriod.setEndDate(null);

        // Create the ExamPeriod, which fails.
        ExamPeriodDTO examPeriodDTO = examPeriodMapper.toDto(examPeriod);

        restExamPeriodMockMvc.perform(post("/api/exam-periods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examPeriodDTO)))
            .andExpect(status().isBadRequest());

        List<ExamPeriod> examPeriodList = examPeriodRepository.findAll();
        assertThat(examPeriodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExamPeriods() throws Exception {
        // Initialize the database
        examPeriodRepository.saveAndFlush(examPeriod);

        // Get all the examPeriodList
        restExamPeriodMockMvc.perform(get("/api/exam-periods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examPeriod.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }

    @Test
    @Transactional
    public void getExamPeriod() throws Exception {
        // Initialize the database
        examPeriodRepository.saveAndFlush(examPeriod);

        // Get the examPeriod
        restExamPeriodMockMvc.perform(get("/api/exam-periods/{id}", examPeriod.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(examPeriod.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExamPeriod() throws Exception {
        // Get the examPeriod
        restExamPeriodMockMvc.perform(get("/api/exam-periods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExamPeriod() throws Exception {
        // Initialize the database
        examPeriodRepository.saveAndFlush(examPeriod);
        examPeriodSearchRepository.save(examPeriod);
        int databaseSizeBeforeUpdate = examPeriodRepository.findAll().size();

        // Update the examPeriod
        ExamPeriod updatedExamPeriod = examPeriodRepository.findOne(examPeriod.getId());
        // Disconnect from session so that the updates on updatedExamPeriod are not directly saved in db
        em.detach(updatedExamPeriod);
        updatedExamPeriod
            .name(UPDATED_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        ExamPeriodDTO examPeriodDTO = examPeriodMapper.toDto(updatedExamPeriod);

        restExamPeriodMockMvc.perform(put("/api/exam-periods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examPeriodDTO)))
            .andExpect(status().isOk());

        // Validate the ExamPeriod in the database
        List<ExamPeriod> examPeriodList = examPeriodRepository.findAll();
        assertThat(examPeriodList).hasSize(databaseSizeBeforeUpdate);
        ExamPeriod testExamPeriod = examPeriodList.get(examPeriodList.size() - 1);
        assertThat(testExamPeriod.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testExamPeriod.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testExamPeriod.getEndDate()).isEqualTo(UPDATED_END_DATE);

        // Validate the ExamPeriod in Elasticsearch
        ExamPeriod examPeriodEs = examPeriodSearchRepository.findOne(testExamPeriod.getId());
        assertThat(examPeriodEs).isEqualToIgnoringGivenFields(testExamPeriod);
    }

    @Test
    @Transactional
    public void updateNonExistingExamPeriod() throws Exception {
        int databaseSizeBeforeUpdate = examPeriodRepository.findAll().size();

        // Create the ExamPeriod
        ExamPeriodDTO examPeriodDTO = examPeriodMapper.toDto(examPeriod);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restExamPeriodMockMvc.perform(put("/api/exam-periods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examPeriodDTO)))
            .andExpect(status().isCreated());

        // Validate the ExamPeriod in the database
        List<ExamPeriod> examPeriodList = examPeriodRepository.findAll();
        assertThat(examPeriodList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteExamPeriod() throws Exception {
        // Initialize the database
        examPeriodRepository.saveAndFlush(examPeriod);
        examPeriodSearchRepository.save(examPeriod);
        int databaseSizeBeforeDelete = examPeriodRepository.findAll().size();

        // Get the examPeriod
        restExamPeriodMockMvc.perform(delete("/api/exam-periods/{id}", examPeriod.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean examPeriodExistsInEs = examPeriodSearchRepository.exists(examPeriod.getId());
        assertThat(examPeriodExistsInEs).isFalse();

        // Validate the database is empty
        List<ExamPeriod> examPeriodList = examPeriodRepository.findAll();
        assertThat(examPeriodList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchExamPeriod() throws Exception {
        // Initialize the database
        examPeriodRepository.saveAndFlush(examPeriod);
        examPeriodSearchRepository.save(examPeriod);

        // Search the examPeriod
        restExamPeriodMockMvc.perform(get("/api/_search/exam-periods?query=id:" + examPeriod.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examPeriod.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamPeriod.class);
        ExamPeriod examPeriod1 = new ExamPeriod();
        examPeriod1.setId(1L);
        ExamPeriod examPeriod2 = new ExamPeriod();
        examPeriod2.setId(examPeriod1.getId());
        assertThat(examPeriod1).isEqualTo(examPeriod2);
        examPeriod2.setId(2L);
        assertThat(examPeriod1).isNotEqualTo(examPeriod2);
        examPeriod1.setId(null);
        assertThat(examPeriod1).isNotEqualTo(examPeriod2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamPeriodDTO.class);
        ExamPeriodDTO examPeriodDTO1 = new ExamPeriodDTO();
        examPeriodDTO1.setId(1L);
        ExamPeriodDTO examPeriodDTO2 = new ExamPeriodDTO();
        assertThat(examPeriodDTO1).isNotEqualTo(examPeriodDTO2);
        examPeriodDTO2.setId(examPeriodDTO1.getId());
        assertThat(examPeriodDTO1).isEqualTo(examPeriodDTO2);
        examPeriodDTO2.setId(2L);
        assertThat(examPeriodDTO1).isNotEqualTo(examPeriodDTO2);
        examPeriodDTO1.setId(null);
        assertThat(examPeriodDTO1).isNotEqualTo(examPeriodDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(examPeriodMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(examPeriodMapper.fromId(null)).isNull();
    }
}
