package irpbc.iteh.web.rest;

import irpbc.iteh.ItehProjectApp;

import irpbc.iteh.domain.Lecturer;
import irpbc.iteh.repository.LecturerRepository;
import irpbc.iteh.service.LecturerService;
import irpbc.iteh.repository.search.LecturerSearchRepository;
import irpbc.iteh.service.dto.LecturerDTO;
import irpbc.iteh.service.mapper.LecturerMapper;
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
 * Test class for the LecturerResource REST controller.
 *
 * @see LecturerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItehProjectApp.class)
public class LecturerResourceIntTest {

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    @Autowired
    private LecturerRepository lecturerRepository;

    @Autowired
    private LecturerMapper lecturerMapper;

    @Autowired
    private LecturerService lecturerService;

    @Autowired
    private LecturerSearchRepository lecturerSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLecturerMockMvc;

    private Lecturer lecturer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LecturerResource lecturerResource = new LecturerResource(lecturerService);
        this.restLecturerMockMvc = MockMvcBuilders.standaloneSetup(lecturerResource)
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
    public static Lecturer createEntity(EntityManager em) {
        Lecturer lecturer = new Lecturer()
            .login(DEFAULT_LOGIN)
            .email(DEFAULT_EMAIL)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME);
        return lecturer;
    }

    @Before
    public void initTest() {
        lecturerSearchRepository.deleteAll();
        lecturer = createEntity(em);
    }

    @Test
    @Transactional
    public void createLecturer() throws Exception {
        int databaseSizeBeforeCreate = lecturerRepository.findAll().size();

        // Create the Lecturer
        LecturerDTO lecturerDTO = lecturerMapper.toDto(lecturer);
        restLecturerMockMvc.perform(post("/api/lecturers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lecturerDTO)))
            .andExpect(status().isCreated());

        // Validate the Lecturer in the database
        List<Lecturer> lecturerList = lecturerRepository.findAll();
        assertThat(lecturerList).hasSize(databaseSizeBeforeCreate + 1);
        Lecturer testLecturer = lecturerList.get(lecturerList.size() - 1);
        assertThat(testLecturer.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testLecturer.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testLecturer.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testLecturer.getLastName()).isEqualTo(DEFAULT_LAST_NAME);

        // Validate the Lecturer in Elasticsearch
        Lecturer lecturerEs = lecturerSearchRepository.findOne(testLecturer.getId());
        assertThat(lecturerEs).isEqualToIgnoringGivenFields(testLecturer);
    }

    @Test
    @Transactional
    public void createLecturerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lecturerRepository.findAll().size();

        // Create the Lecturer with an existing ID
        lecturer.setId(1L);
        LecturerDTO lecturerDTO = lecturerMapper.toDto(lecturer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLecturerMockMvc.perform(post("/api/lecturers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lecturerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lecturer in the database
        List<Lecturer> lecturerList = lecturerRepository.findAll();
        assertThat(lecturerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLoginIsRequired() throws Exception {
        int databaseSizeBeforeTest = lecturerRepository.findAll().size();
        // set the field null
        lecturer.setLogin(null);

        // Create the Lecturer, which fails.
        LecturerDTO lecturerDTO = lecturerMapper.toDto(lecturer);

        restLecturerMockMvc.perform(post("/api/lecturers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lecturerDTO)))
            .andExpect(status().isBadRequest());

        List<Lecturer> lecturerList = lecturerRepository.findAll();
        assertThat(lecturerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = lecturerRepository.findAll().size();
        // set the field null
        lecturer.setEmail(null);

        // Create the Lecturer, which fails.
        LecturerDTO lecturerDTO = lecturerMapper.toDto(lecturer);

        restLecturerMockMvc.perform(post("/api/lecturers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lecturerDTO)))
            .andExpect(status().isBadRequest());

        List<Lecturer> lecturerList = lecturerRepository.findAll();
        assertThat(lecturerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = lecturerRepository.findAll().size();
        // set the field null
        lecturer.setFirstName(null);

        // Create the Lecturer, which fails.
        LecturerDTO lecturerDTO = lecturerMapper.toDto(lecturer);

        restLecturerMockMvc.perform(post("/api/lecturers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lecturerDTO)))
            .andExpect(status().isBadRequest());

        List<Lecturer> lecturerList = lecturerRepository.findAll();
        assertThat(lecturerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = lecturerRepository.findAll().size();
        // set the field null
        lecturer.setLastName(null);

        // Create the Lecturer, which fails.
        LecturerDTO lecturerDTO = lecturerMapper.toDto(lecturer);

        restLecturerMockMvc.perform(post("/api/lecturers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lecturerDTO)))
            .andExpect(status().isBadRequest());

        List<Lecturer> lecturerList = lecturerRepository.findAll();
        assertThat(lecturerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLecturers() throws Exception {
        // Initialize the database
        lecturerRepository.saveAndFlush(lecturer);

        // Get all the lecturerList
        restLecturerMockMvc.perform(get("/api/lecturers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lecturer.getId().intValue())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())));
    }

    @Test
    @Transactional
    public void getLecturer() throws Exception {
        // Initialize the database
        lecturerRepository.saveAndFlush(lecturer);

        // Get the lecturer
        restLecturerMockMvc.perform(get("/api/lecturers/{id}", lecturer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lecturer.getId().intValue()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLecturer() throws Exception {
        // Get the lecturer
        restLecturerMockMvc.perform(get("/api/lecturers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLecturer() throws Exception {
        // Initialize the database
        lecturerRepository.saveAndFlush(lecturer);
        lecturerSearchRepository.save(lecturer);
        int databaseSizeBeforeUpdate = lecturerRepository.findAll().size();

        // Update the lecturer
        Lecturer updatedLecturer = lecturerRepository.findOne(lecturer.getId());
        // Disconnect from session so that the updates on updatedLecturer are not directly saved in db
        em.detach(updatedLecturer);
        updatedLecturer
            .login(UPDATED_LOGIN)
            .email(UPDATED_EMAIL)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME);
        LecturerDTO lecturerDTO = lecturerMapper.toDto(updatedLecturer);

        restLecturerMockMvc.perform(put("/api/lecturers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lecturerDTO)))
            .andExpect(status().isOk());

        // Validate the Lecturer in the database
        List<Lecturer> lecturerList = lecturerRepository.findAll();
        assertThat(lecturerList).hasSize(databaseSizeBeforeUpdate);
        Lecturer testLecturer = lecturerList.get(lecturerList.size() - 1);
        assertThat(testLecturer.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testLecturer.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testLecturer.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testLecturer.getLastName()).isEqualTo(UPDATED_LAST_NAME);

        // Validate the Lecturer in Elasticsearch
        Lecturer lecturerEs = lecturerSearchRepository.findOne(testLecturer.getId());
        assertThat(lecturerEs).isEqualToIgnoringGivenFields(testLecturer);
    }

    @Test
    @Transactional
    public void updateNonExistingLecturer() throws Exception {
        int databaseSizeBeforeUpdate = lecturerRepository.findAll().size();

        // Create the Lecturer
        LecturerDTO lecturerDTO = lecturerMapper.toDto(lecturer);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLecturerMockMvc.perform(put("/api/lecturers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lecturerDTO)))
            .andExpect(status().isCreated());

        // Validate the Lecturer in the database
        List<Lecturer> lecturerList = lecturerRepository.findAll();
        assertThat(lecturerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLecturer() throws Exception {
        // Initialize the database
        lecturerRepository.saveAndFlush(lecturer);
        lecturerSearchRepository.save(lecturer);
        int databaseSizeBeforeDelete = lecturerRepository.findAll().size();

        // Get the lecturer
        restLecturerMockMvc.perform(delete("/api/lecturers/{id}", lecturer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean lecturerExistsInEs = lecturerSearchRepository.exists(lecturer.getId());
        assertThat(lecturerExistsInEs).isFalse();

        // Validate the database is empty
        List<Lecturer> lecturerList = lecturerRepository.findAll();
        assertThat(lecturerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchLecturer() throws Exception {
        // Initialize the database
        lecturerRepository.saveAndFlush(lecturer);
        lecturerSearchRepository.save(lecturer);

        // Search the lecturer
        restLecturerMockMvc.perform(get("/api/_search/lecturers?query=id:" + lecturer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lecturer.getId().intValue())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lecturer.class);
        Lecturer lecturer1 = new Lecturer();
        lecturer1.setId(1L);
        Lecturer lecturer2 = new Lecturer();
        lecturer2.setId(lecturer1.getId());
        assertThat(lecturer1).isEqualTo(lecturer2);
        lecturer2.setId(2L);
        assertThat(lecturer1).isNotEqualTo(lecturer2);
        lecturer1.setId(null);
        assertThat(lecturer1).isNotEqualTo(lecturer2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LecturerDTO.class);
        LecturerDTO lecturerDTO1 = new LecturerDTO();
        lecturerDTO1.setId(1L);
        LecturerDTO lecturerDTO2 = new LecturerDTO();
        assertThat(lecturerDTO1).isNotEqualTo(lecturerDTO2);
        lecturerDTO2.setId(lecturerDTO1.getId());
        assertThat(lecturerDTO1).isEqualTo(lecturerDTO2);
        lecturerDTO2.setId(2L);
        assertThat(lecturerDTO1).isNotEqualTo(lecturerDTO2);
        lecturerDTO1.setId(null);
        assertThat(lecturerDTO1).isNotEqualTo(lecturerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(lecturerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(lecturerMapper.fromId(null)).isNull();
    }
}
