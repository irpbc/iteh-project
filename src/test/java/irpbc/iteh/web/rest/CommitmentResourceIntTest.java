package irpbc.iteh.web.rest;

import irpbc.iteh.ItehProjectApp;

import irpbc.iteh.domain.Commitment;
import irpbc.iteh.domain.Course;
import irpbc.iteh.repository.CommitmentRepository;
import irpbc.iteh.service.CommitmentService;
import irpbc.iteh.repository.search.CommitmentSearchRepository;
import irpbc.iteh.service.dto.CommitmentDTO;
import irpbc.iteh.service.mapper.CommitmentMapper;
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
 * Test class for the CommitmentResource REST controller.
 *
 * @see CommitmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItehProjectApp.class)
public class CommitmentResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_MAX_POINTS = new BigDecimal(1);
    private static final BigDecimal UPDATED_MAX_POINTS = new BigDecimal(2);

    @Autowired
    private CommitmentRepository commitmentRepository;

    @Autowired
    private CommitmentMapper commitmentMapper;

    @Autowired
    private CommitmentService commitmentService;

    @Autowired
    private CommitmentSearchRepository commitmentSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommitmentMockMvc;

    private Commitment commitment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommitmentResource commitmentResource = new CommitmentResource(commitmentService);
        this.restCommitmentMockMvc = MockMvcBuilders.standaloneSetup(commitmentResource)
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
    public static Commitment createEntity(EntityManager em) {
        Commitment commitment = new Commitment()
            .name(DEFAULT_NAME)
            .maxPoints(DEFAULT_MAX_POINTS);
        // Add required entity
        Course course = CourseResourceIntTest.createEntity(em);
        em.persist(course);
        em.flush();
        commitment.setCourse(course);
        return commitment;
    }

    @Before
    public void initTest() {
        commitmentSearchRepository.deleteAll();
        commitment = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommitment() throws Exception {
        int databaseSizeBeforeCreate = commitmentRepository.findAll().size();

        // Create the Commitment
        CommitmentDTO commitmentDTO = commitmentMapper.toDto(commitment);
        restCommitmentMockMvc.perform(post("/api/commitments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commitmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Commitment in the database
        List<Commitment> commitmentList = commitmentRepository.findAll();
        assertThat(commitmentList).hasSize(databaseSizeBeforeCreate + 1);
        Commitment testCommitment = commitmentList.get(commitmentList.size() - 1);
        assertThat(testCommitment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCommitment.getMaxPoints()).isEqualTo(DEFAULT_MAX_POINTS);

        // Validate the Commitment in Elasticsearch
        Commitment commitmentEs = commitmentSearchRepository.findOne(testCommitment.getId());
        assertThat(commitmentEs).isEqualToIgnoringGivenFields(testCommitment);
    }

    @Test
    @Transactional
    public void createCommitmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commitmentRepository.findAll().size();

        // Create the Commitment with an existing ID
        commitment.setId(1L);
        CommitmentDTO commitmentDTO = commitmentMapper.toDto(commitment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommitmentMockMvc.perform(post("/api/commitments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commitmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Commitment in the database
        List<Commitment> commitmentList = commitmentRepository.findAll();
        assertThat(commitmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = commitmentRepository.findAll().size();
        // set the field null
        commitment.setName(null);

        // Create the Commitment, which fails.
        CommitmentDTO commitmentDTO = commitmentMapper.toDto(commitment);

        restCommitmentMockMvc.perform(post("/api/commitments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commitmentDTO)))
            .andExpect(status().isBadRequest());

        List<Commitment> commitmentList = commitmentRepository.findAll();
        assertThat(commitmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMaxPointsIsRequired() throws Exception {
        int databaseSizeBeforeTest = commitmentRepository.findAll().size();
        // set the field null
        commitment.setMaxPoints(null);

        // Create the Commitment, which fails.
        CommitmentDTO commitmentDTO = commitmentMapper.toDto(commitment);

        restCommitmentMockMvc.perform(post("/api/commitments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commitmentDTO)))
            .andExpect(status().isBadRequest());

        List<Commitment> commitmentList = commitmentRepository.findAll();
        assertThat(commitmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommitments() throws Exception {
        // Initialize the database
        commitmentRepository.saveAndFlush(commitment);

        // Get all the commitmentList
        restCommitmentMockMvc.perform(get("/api/commitments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commitment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].maxPoints").value(hasItem(DEFAULT_MAX_POINTS.intValue())));
    }

    @Test
    @Transactional
    public void getCommitment() throws Exception {
        // Initialize the database
        commitmentRepository.saveAndFlush(commitment);

        // Get the commitment
        restCommitmentMockMvc.perform(get("/api/commitments/{id}", commitment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commitment.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.maxPoints").value(DEFAULT_MAX_POINTS.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCommitment() throws Exception {
        // Get the commitment
        restCommitmentMockMvc.perform(get("/api/commitments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommitment() throws Exception {
        // Initialize the database
        commitmentRepository.saveAndFlush(commitment);
        commitmentSearchRepository.save(commitment);
        int databaseSizeBeforeUpdate = commitmentRepository.findAll().size();

        // Update the commitment
        Commitment updatedCommitment = commitmentRepository.findOne(commitment.getId());
        // Disconnect from session so that the updates on updatedCommitment are not directly saved in db
        em.detach(updatedCommitment);
        updatedCommitment
            .name(UPDATED_NAME)
            .maxPoints(UPDATED_MAX_POINTS);
        CommitmentDTO commitmentDTO = commitmentMapper.toDto(updatedCommitment);

        restCommitmentMockMvc.perform(put("/api/commitments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commitmentDTO)))
            .andExpect(status().isOk());

        // Validate the Commitment in the database
        List<Commitment> commitmentList = commitmentRepository.findAll();
        assertThat(commitmentList).hasSize(databaseSizeBeforeUpdate);
        Commitment testCommitment = commitmentList.get(commitmentList.size() - 1);
        assertThat(testCommitment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCommitment.getMaxPoints()).isEqualTo(UPDATED_MAX_POINTS);

        // Validate the Commitment in Elasticsearch
        Commitment commitmentEs = commitmentSearchRepository.findOne(testCommitment.getId());
        assertThat(commitmentEs).isEqualToIgnoringGivenFields(testCommitment);
    }

    @Test
    @Transactional
    public void updateNonExistingCommitment() throws Exception {
        int databaseSizeBeforeUpdate = commitmentRepository.findAll().size();

        // Create the Commitment
        CommitmentDTO commitmentDTO = commitmentMapper.toDto(commitment);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCommitmentMockMvc.perform(put("/api/commitments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commitmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Commitment in the database
        List<Commitment> commitmentList = commitmentRepository.findAll();
        assertThat(commitmentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCommitment() throws Exception {
        // Initialize the database
        commitmentRepository.saveAndFlush(commitment);
        commitmentSearchRepository.save(commitment);
        int databaseSizeBeforeDelete = commitmentRepository.findAll().size();

        // Get the commitment
        restCommitmentMockMvc.perform(delete("/api/commitments/{id}", commitment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean commitmentExistsInEs = commitmentSearchRepository.exists(commitment.getId());
        assertThat(commitmentExistsInEs).isFalse();

        // Validate the database is empty
        List<Commitment> commitmentList = commitmentRepository.findAll();
        assertThat(commitmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCommitment() throws Exception {
        // Initialize the database
        commitmentRepository.saveAndFlush(commitment);
        commitmentSearchRepository.save(commitment);

        // Search the commitment
        restCommitmentMockMvc.perform(get("/api/_search/commitments?query=id:" + commitment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commitment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].maxPoints").value(hasItem(DEFAULT_MAX_POINTS.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Commitment.class);
        Commitment commitment1 = new Commitment();
        commitment1.setId(1L);
        Commitment commitment2 = new Commitment();
        commitment2.setId(commitment1.getId());
        assertThat(commitment1).isEqualTo(commitment2);
        commitment2.setId(2L);
        assertThat(commitment1).isNotEqualTo(commitment2);
        commitment1.setId(null);
        assertThat(commitment1).isNotEqualTo(commitment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommitmentDTO.class);
        CommitmentDTO commitmentDTO1 = new CommitmentDTO();
        commitmentDTO1.setId(1L);
        CommitmentDTO commitmentDTO2 = new CommitmentDTO();
        assertThat(commitmentDTO1).isNotEqualTo(commitmentDTO2);
        commitmentDTO2.setId(commitmentDTO1.getId());
        assertThat(commitmentDTO1).isEqualTo(commitmentDTO2);
        commitmentDTO2.setId(2L);
        assertThat(commitmentDTO1).isNotEqualTo(commitmentDTO2);
        commitmentDTO1.setId(null);
        assertThat(commitmentDTO1).isNotEqualTo(commitmentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commitmentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commitmentMapper.fromId(null)).isNull();
    }
}
