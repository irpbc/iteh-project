package irpbc.iteh.web.rest;

import irpbc.iteh.ItehProjectApp;

import irpbc.iteh.domain.FacebookPostProposal;
import irpbc.iteh.domain.User;
import irpbc.iteh.repository.FacebookPostProposalRepository;
import irpbc.iteh.service.FacebookPostProposalService;
import irpbc.iteh.service.dto.FacebookPostProposalDTO;
import irpbc.iteh.service.mapper.FacebookPostProposalMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static irpbc.iteh.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import irpbc.iteh.domain.enumeration.FacebookPostKind;
/**
 * Test class for the FacebookPostProposalResource REST controller.
 *
 * @see FacebookPostProposalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItehProjectApp.class)
public class FacebookPostProposalResourceIntTest {

    private static final FacebookPostKind DEFAULT_KIND = FacebookPostKind.EXAM_PASSED;
    private static final FacebookPostKind UPDATED_KIND = FacebookPostKind.EXAM_FAILED;

    private static final String DEFAULT_DATA = "AAAAAAAAAA";
    private static final String UPDATED_DATA = "BBBBBBBBBB";

    private static final Instant DEFAULT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private FacebookPostProposalRepository facebookPostProposalRepository;

    @Autowired
    private FacebookPostProposalMapper facebookPostProposalMapper;

    @Autowired
    private FacebookPostProposalService facebookPostProposalService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFacebookPostProposalMockMvc;

    private FacebookPostProposal facebookPostProposal;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FacebookPostProposalResource facebookPostProposalResource = new FacebookPostProposalResource(facebookPostProposalService);
        this.restFacebookPostProposalMockMvc = MockMvcBuilders.standaloneSetup(facebookPostProposalResource)
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
    public static FacebookPostProposal createEntity(EntityManager em) {
        FacebookPostProposal facebookPostProposal = new FacebookPostProposal()
            .kind(DEFAULT_KIND)
            .data(DEFAULT_DATA)
            .time(DEFAULT_TIME);
        // Add required entity
        User student = UserResourceIntTest.createEntity(em);
        em.persist(student);
        em.flush();
        facebookPostProposal.setStudent(student);
        return facebookPostProposal;
    }

    @Before
    public void initTest() {
        facebookPostProposal = createEntity(em);
    }

    @Test
    @Transactional
    public void createFacebookPostProposal() throws Exception {
        int databaseSizeBeforeCreate = facebookPostProposalRepository.findAll().size();

        // Create the FacebookPostProposal
        FacebookPostProposalDTO facebookPostProposalDTO = facebookPostProposalMapper.toDto(facebookPostProposal);
        restFacebookPostProposalMockMvc.perform(post("/api/facebook-post-proposals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facebookPostProposalDTO)))
            .andExpect(status().isCreated());

        // Validate the FacebookPostProposal in the database
        List<FacebookPostProposal> facebookPostProposalList = facebookPostProposalRepository.findAll();
        assertThat(facebookPostProposalList).hasSize(databaseSizeBeforeCreate + 1);
        FacebookPostProposal testFacebookPostProposal = facebookPostProposalList.get(facebookPostProposalList.size() - 1);
        assertThat(testFacebookPostProposal.getKind()).isEqualTo(DEFAULT_KIND);
        assertThat(testFacebookPostProposal.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testFacebookPostProposal.getTime()).isEqualTo(DEFAULT_TIME);
    }

    @Test
    @Transactional
    public void createFacebookPostProposalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = facebookPostProposalRepository.findAll().size();

        // Create the FacebookPostProposal with an existing ID
        facebookPostProposal.setId(1L);
        FacebookPostProposalDTO facebookPostProposalDTO = facebookPostProposalMapper.toDto(facebookPostProposal);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFacebookPostProposalMockMvc.perform(post("/api/facebook-post-proposals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facebookPostProposalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FacebookPostProposal in the database
        List<FacebookPostProposal> facebookPostProposalList = facebookPostProposalRepository.findAll();
        assertThat(facebookPostProposalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkKindIsRequired() throws Exception {
        int databaseSizeBeforeTest = facebookPostProposalRepository.findAll().size();
        // set the field null
        facebookPostProposal.setKind(null);

        // Create the FacebookPostProposal, which fails.
        FacebookPostProposalDTO facebookPostProposalDTO = facebookPostProposalMapper.toDto(facebookPostProposal);

        restFacebookPostProposalMockMvc.perform(post("/api/facebook-post-proposals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facebookPostProposalDTO)))
            .andExpect(status().isBadRequest());

        List<FacebookPostProposal> facebookPostProposalList = facebookPostProposalRepository.findAll();
        assertThat(facebookPostProposalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = facebookPostProposalRepository.findAll().size();
        // set the field null
        facebookPostProposal.setData(null);

        // Create the FacebookPostProposal, which fails.
        FacebookPostProposalDTO facebookPostProposalDTO = facebookPostProposalMapper.toDto(facebookPostProposal);

        restFacebookPostProposalMockMvc.perform(post("/api/facebook-post-proposals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facebookPostProposalDTO)))
            .andExpect(status().isBadRequest());

        List<FacebookPostProposal> facebookPostProposalList = facebookPostProposalRepository.findAll();
        assertThat(facebookPostProposalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = facebookPostProposalRepository.findAll().size();
        // set the field null
        facebookPostProposal.setTime(null);

        // Create the FacebookPostProposal, which fails.
        FacebookPostProposalDTO facebookPostProposalDTO = facebookPostProposalMapper.toDto(facebookPostProposal);

        restFacebookPostProposalMockMvc.perform(post("/api/facebook-post-proposals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facebookPostProposalDTO)))
            .andExpect(status().isBadRequest());

        List<FacebookPostProposal> facebookPostProposalList = facebookPostProposalRepository.findAll();
        assertThat(facebookPostProposalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFacebookPostProposals() throws Exception {
        // Initialize the database
        facebookPostProposalRepository.saveAndFlush(facebookPostProposal);

        // Get all the facebookPostProposalList
        restFacebookPostProposalMockMvc.perform(get("/api/facebook-post-proposals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facebookPostProposal.getId().intValue())))
            .andExpect(jsonPath("$.[*].kind").value(hasItem(DEFAULT_KIND.toString())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.toString())));
    }

    @Test
    @Transactional
    public void getFacebookPostProposal() throws Exception {
        // Initialize the database
        facebookPostProposalRepository.saveAndFlush(facebookPostProposal);

        // Get the facebookPostProposal
        restFacebookPostProposalMockMvc.perform(get("/api/facebook-post-proposals/{id}", facebookPostProposal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(facebookPostProposal.getId().intValue()))
            .andExpect(jsonPath("$.kind").value(DEFAULT_KIND.toString()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFacebookPostProposal() throws Exception {
        // Get the facebookPostProposal
        restFacebookPostProposalMockMvc.perform(get("/api/facebook-post-proposals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFacebookPostProposal() throws Exception {
        // Initialize the database
        facebookPostProposalRepository.saveAndFlush(facebookPostProposal);
        int databaseSizeBeforeUpdate = facebookPostProposalRepository.findAll().size();

        // Update the facebookPostProposal
        FacebookPostProposal updatedFacebookPostProposal = facebookPostProposalRepository.findOne(facebookPostProposal.getId());
        // Disconnect from session so that the updates on updatedFacebookPostProposal are not directly saved in db
        em.detach(updatedFacebookPostProposal);
        updatedFacebookPostProposal
            .kind(UPDATED_KIND)
            .data(UPDATED_DATA)
            .time(UPDATED_TIME);
        FacebookPostProposalDTO facebookPostProposalDTO = facebookPostProposalMapper.toDto(updatedFacebookPostProposal);

        restFacebookPostProposalMockMvc.perform(put("/api/facebook-post-proposals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facebookPostProposalDTO)))
            .andExpect(status().isOk());

        // Validate the FacebookPostProposal in the database
        List<FacebookPostProposal> facebookPostProposalList = facebookPostProposalRepository.findAll();
        assertThat(facebookPostProposalList).hasSize(databaseSizeBeforeUpdate);
        FacebookPostProposal testFacebookPostProposal = facebookPostProposalList.get(facebookPostProposalList.size() - 1);
        assertThat(testFacebookPostProposal.getKind()).isEqualTo(UPDATED_KIND);
        assertThat(testFacebookPostProposal.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testFacebookPostProposal.getTime()).isEqualTo(UPDATED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingFacebookPostProposal() throws Exception {
        int databaseSizeBeforeUpdate = facebookPostProposalRepository.findAll().size();

        // Create the FacebookPostProposal
        FacebookPostProposalDTO facebookPostProposalDTO = facebookPostProposalMapper.toDto(facebookPostProposal);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFacebookPostProposalMockMvc.perform(put("/api/facebook-post-proposals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facebookPostProposalDTO)))
            .andExpect(status().isCreated());

        // Validate the FacebookPostProposal in the database
        List<FacebookPostProposal> facebookPostProposalList = facebookPostProposalRepository.findAll();
        assertThat(facebookPostProposalList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFacebookPostProposal() throws Exception {
        // Initialize the database
        facebookPostProposalRepository.saveAndFlush(facebookPostProposal);
        int databaseSizeBeforeDelete = facebookPostProposalRepository.findAll().size();

        // Get the facebookPostProposal
        restFacebookPostProposalMockMvc.perform(delete("/api/facebook-post-proposals/{id}", facebookPostProposal.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FacebookPostProposal> facebookPostProposalList = facebookPostProposalRepository.findAll();
        assertThat(facebookPostProposalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FacebookPostProposal.class);
        FacebookPostProposal facebookPostProposal1 = new FacebookPostProposal();
        facebookPostProposal1.setId(1L);
        FacebookPostProposal facebookPostProposal2 = new FacebookPostProposal();
        facebookPostProposal2.setId(facebookPostProposal1.getId());
        assertThat(facebookPostProposal1).isEqualTo(facebookPostProposal2);
        facebookPostProposal2.setId(2L);
        assertThat(facebookPostProposal1).isNotEqualTo(facebookPostProposal2);
        facebookPostProposal1.setId(null);
        assertThat(facebookPostProposal1).isNotEqualTo(facebookPostProposal2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FacebookPostProposalDTO.class);
        FacebookPostProposalDTO facebookPostProposalDTO1 = new FacebookPostProposalDTO();
        facebookPostProposalDTO1.setId(1L);
        FacebookPostProposalDTO facebookPostProposalDTO2 = new FacebookPostProposalDTO();
        assertThat(facebookPostProposalDTO1).isNotEqualTo(facebookPostProposalDTO2);
        facebookPostProposalDTO2.setId(facebookPostProposalDTO1.getId());
        assertThat(facebookPostProposalDTO1).isEqualTo(facebookPostProposalDTO2);
        facebookPostProposalDTO2.setId(2L);
        assertThat(facebookPostProposalDTO1).isNotEqualTo(facebookPostProposalDTO2);
        facebookPostProposalDTO1.setId(null);
        assertThat(facebookPostProposalDTO1).isNotEqualTo(facebookPostProposalDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(facebookPostProposalMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(facebookPostProposalMapper.fromId(null)).isNull();
    }
}
