package irpbc.iteh.web.rest;

import irpbc.iteh.ItehProjectApp;

import irpbc.iteh.domain.OptionalCourseGroup;
import irpbc.iteh.domain.Semester;
import irpbc.iteh.repository.OptionalCourseGroupRepository;
import irpbc.iteh.service.OptionalCourseGroupService;
import irpbc.iteh.repository.search.OptionalCourseGroupSearchRepository;
import irpbc.iteh.service.dto.OptionalCourseGroupDTO;
import irpbc.iteh.service.mapper.OptionalCourseGroupMapper;
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
 * Test class for the OptionalCourseGroupResource REST controller.
 *
 * @see OptionalCourseGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItehProjectApp.class)
public class OptionalCourseGroupResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private OptionalCourseGroupRepository optionalCourseGroupRepository;

    @Autowired
    private OptionalCourseGroupMapper optionalCourseGroupMapper;

    @Autowired
    private OptionalCourseGroupService optionalCourseGroupService;

    @Autowired
    private OptionalCourseGroupSearchRepository optionalCourseGroupSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOptionalCourseGroupMockMvc;

    private OptionalCourseGroup optionalCourseGroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OptionalCourseGroupResource optionalCourseGroupResource = new OptionalCourseGroupResource(optionalCourseGroupService);
        this.restOptionalCourseGroupMockMvc = MockMvcBuilders.standaloneSetup(optionalCourseGroupResource)
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
    public static OptionalCourseGroup createEntity(EntityManager em) {
        OptionalCourseGroup optionalCourseGroup = new OptionalCourseGroup()
            .name(DEFAULT_NAME);
        // Add required entity
        Semester semester = SemesterResourceIntTest.createEntity(em);
        em.persist(semester);
        em.flush();
        optionalCourseGroup.setSemester(semester);
        return optionalCourseGroup;
    }

    @Before
    public void initTest() {
        optionalCourseGroupSearchRepository.deleteAll();
        optionalCourseGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createOptionalCourseGroup() throws Exception {
        int databaseSizeBeforeCreate = optionalCourseGroupRepository.findAll().size();

        // Create the OptionalCourseGroup
        OptionalCourseGroupDTO optionalCourseGroupDTO = optionalCourseGroupMapper.toDto(optionalCourseGroup);
        restOptionalCourseGroupMockMvc.perform(post("/api/optional-course-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(optionalCourseGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the OptionalCourseGroup in the database
        List<OptionalCourseGroup> optionalCourseGroupList = optionalCourseGroupRepository.findAll();
        assertThat(optionalCourseGroupList).hasSize(databaseSizeBeforeCreate + 1);
        OptionalCourseGroup testOptionalCourseGroup = optionalCourseGroupList.get(optionalCourseGroupList.size() - 1);
        assertThat(testOptionalCourseGroup.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the OptionalCourseGroup in Elasticsearch
        OptionalCourseGroup optionalCourseGroupEs = optionalCourseGroupSearchRepository.findOne(testOptionalCourseGroup.getId());
        assertThat(optionalCourseGroupEs).isEqualToIgnoringGivenFields(testOptionalCourseGroup);
    }

    @Test
    @Transactional
    public void createOptionalCourseGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = optionalCourseGroupRepository.findAll().size();

        // Create the OptionalCourseGroup with an existing ID
        optionalCourseGroup.setId(1L);
        OptionalCourseGroupDTO optionalCourseGroupDTO = optionalCourseGroupMapper.toDto(optionalCourseGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOptionalCourseGroupMockMvc.perform(post("/api/optional-course-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(optionalCourseGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OptionalCourseGroup in the database
        List<OptionalCourseGroup> optionalCourseGroupList = optionalCourseGroupRepository.findAll();
        assertThat(optionalCourseGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = optionalCourseGroupRepository.findAll().size();
        // set the field null
        optionalCourseGroup.setName(null);

        // Create the OptionalCourseGroup, which fails.
        OptionalCourseGroupDTO optionalCourseGroupDTO = optionalCourseGroupMapper.toDto(optionalCourseGroup);

        restOptionalCourseGroupMockMvc.perform(post("/api/optional-course-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(optionalCourseGroupDTO)))
            .andExpect(status().isBadRequest());

        List<OptionalCourseGroup> optionalCourseGroupList = optionalCourseGroupRepository.findAll();
        assertThat(optionalCourseGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOptionalCourseGroups() throws Exception {
        // Initialize the database
        optionalCourseGroupRepository.saveAndFlush(optionalCourseGroup);

        // Get all the optionalCourseGroupList
        restOptionalCourseGroupMockMvc.perform(get("/api/optional-course-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(optionalCourseGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getOptionalCourseGroup() throws Exception {
        // Initialize the database
        optionalCourseGroupRepository.saveAndFlush(optionalCourseGroup);

        // Get the optionalCourseGroup
        restOptionalCourseGroupMockMvc.perform(get("/api/optional-course-groups/{id}", optionalCourseGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(optionalCourseGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOptionalCourseGroup() throws Exception {
        // Get the optionalCourseGroup
        restOptionalCourseGroupMockMvc.perform(get("/api/optional-course-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOptionalCourseGroup() throws Exception {
        // Initialize the database
        optionalCourseGroupRepository.saveAndFlush(optionalCourseGroup);
        optionalCourseGroupSearchRepository.save(optionalCourseGroup);
        int databaseSizeBeforeUpdate = optionalCourseGroupRepository.findAll().size();

        // Update the optionalCourseGroup
        OptionalCourseGroup updatedOptionalCourseGroup = optionalCourseGroupRepository.findOne(optionalCourseGroup.getId());
        // Disconnect from session so that the updates on updatedOptionalCourseGroup are not directly saved in db
        em.detach(updatedOptionalCourseGroup);
        updatedOptionalCourseGroup
            .name(UPDATED_NAME);
        OptionalCourseGroupDTO optionalCourseGroupDTO = optionalCourseGroupMapper.toDto(updatedOptionalCourseGroup);

        restOptionalCourseGroupMockMvc.perform(put("/api/optional-course-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(optionalCourseGroupDTO)))
            .andExpect(status().isOk());

        // Validate the OptionalCourseGroup in the database
        List<OptionalCourseGroup> optionalCourseGroupList = optionalCourseGroupRepository.findAll();
        assertThat(optionalCourseGroupList).hasSize(databaseSizeBeforeUpdate);
        OptionalCourseGroup testOptionalCourseGroup = optionalCourseGroupList.get(optionalCourseGroupList.size() - 1);
        assertThat(testOptionalCourseGroup.getName()).isEqualTo(UPDATED_NAME);

        // Validate the OptionalCourseGroup in Elasticsearch
        OptionalCourseGroup optionalCourseGroupEs = optionalCourseGroupSearchRepository.findOne(testOptionalCourseGroup.getId());
        assertThat(optionalCourseGroupEs).isEqualToIgnoringGivenFields(testOptionalCourseGroup);
    }

    @Test
    @Transactional
    public void updateNonExistingOptionalCourseGroup() throws Exception {
        int databaseSizeBeforeUpdate = optionalCourseGroupRepository.findAll().size();

        // Create the OptionalCourseGroup
        OptionalCourseGroupDTO optionalCourseGroupDTO = optionalCourseGroupMapper.toDto(optionalCourseGroup);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOptionalCourseGroupMockMvc.perform(put("/api/optional-course-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(optionalCourseGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the OptionalCourseGroup in the database
        List<OptionalCourseGroup> optionalCourseGroupList = optionalCourseGroupRepository.findAll();
        assertThat(optionalCourseGroupList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOptionalCourseGroup() throws Exception {
        // Initialize the database
        optionalCourseGroupRepository.saveAndFlush(optionalCourseGroup);
        optionalCourseGroupSearchRepository.save(optionalCourseGroup);
        int databaseSizeBeforeDelete = optionalCourseGroupRepository.findAll().size();

        // Get the optionalCourseGroup
        restOptionalCourseGroupMockMvc.perform(delete("/api/optional-course-groups/{id}", optionalCourseGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean optionalCourseGroupExistsInEs = optionalCourseGroupSearchRepository.exists(optionalCourseGroup.getId());
        assertThat(optionalCourseGroupExistsInEs).isFalse();

        // Validate the database is empty
        List<OptionalCourseGroup> optionalCourseGroupList = optionalCourseGroupRepository.findAll();
        assertThat(optionalCourseGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchOptionalCourseGroup() throws Exception {
        // Initialize the database
        optionalCourseGroupRepository.saveAndFlush(optionalCourseGroup);
        optionalCourseGroupSearchRepository.save(optionalCourseGroup);

        // Search the optionalCourseGroup
        restOptionalCourseGroupMockMvc.perform(get("/api/_search/optional-course-groups?query=id:" + optionalCourseGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(optionalCourseGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OptionalCourseGroup.class);
        OptionalCourseGroup optionalCourseGroup1 = new OptionalCourseGroup();
        optionalCourseGroup1.setId(1L);
        OptionalCourseGroup optionalCourseGroup2 = new OptionalCourseGroup();
        optionalCourseGroup2.setId(optionalCourseGroup1.getId());
        assertThat(optionalCourseGroup1).isEqualTo(optionalCourseGroup2);
        optionalCourseGroup2.setId(2L);
        assertThat(optionalCourseGroup1).isNotEqualTo(optionalCourseGroup2);
        optionalCourseGroup1.setId(null);
        assertThat(optionalCourseGroup1).isNotEqualTo(optionalCourseGroup2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OptionalCourseGroupDTO.class);
        OptionalCourseGroupDTO optionalCourseGroupDTO1 = new OptionalCourseGroupDTO();
        optionalCourseGroupDTO1.setId(1L);
        OptionalCourseGroupDTO optionalCourseGroupDTO2 = new OptionalCourseGroupDTO();
        assertThat(optionalCourseGroupDTO1).isNotEqualTo(optionalCourseGroupDTO2);
        optionalCourseGroupDTO2.setId(optionalCourseGroupDTO1.getId());
        assertThat(optionalCourseGroupDTO1).isEqualTo(optionalCourseGroupDTO2);
        optionalCourseGroupDTO2.setId(2L);
        assertThat(optionalCourseGroupDTO1).isNotEqualTo(optionalCourseGroupDTO2);
        optionalCourseGroupDTO1.setId(null);
        assertThat(optionalCourseGroupDTO1).isNotEqualTo(optionalCourseGroupDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(optionalCourseGroupMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(optionalCourseGroupMapper.fromId(null)).isNull();
    }
}
