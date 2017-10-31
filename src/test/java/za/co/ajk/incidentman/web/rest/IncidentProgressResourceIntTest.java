package za.co.ajk.incidentman.web.rest;

import za.co.ajk.incidentman.EventModuleApp;

import za.co.ajk.incidentman.domain.IncidentProgress;
import za.co.ajk.incidentman.repository.IncidentProgressRepository;
import za.co.ajk.incidentman.service.IncidentProgressService;
import za.co.ajk.incidentman.service.dto.IncidentProgressDTO;
import za.co.ajk.incidentman.service.mapper.IncidentProgressMapper;
import za.co.ajk.incidentman.web.rest.errors.ExceptionTranslator;

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

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static za.co.ajk.incidentman.web.rest.TestUtil.sameInstant;
import static za.co.ajk.incidentman.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import za.co.ajk.incidentman.domain.enumeration.Priority;
/**
 * Test class for the IncidentProgressResource REST controller.
 *
 * @see IncidentProgressResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EventModuleApp.class)
public class IncidentProgressResourceIntTest {

    private static final Integer DEFAULT_INCIDENT_NUMBER = 1;
    private static final Integer UPDATED_INCIDENT_NUMBER = 2;

    private static final Integer DEFAULT_PROGRESS_NUMBER = 1;
    private static final Integer UPDATED_PROGRESS_NUMBER = 2;

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_UPDATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_UPDATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Priority DEFAULT_UPDATED_PRIORITY = Priority.LOW;
    private static final Priority UPDATED_UPDATED_PRIORITY = Priority.MEDIUM;

    private static final String DEFAULT_PROGRESS_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PROGRESS_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_LOAN_EQUIPMENT = "AAAAAAAAAA";
    private static final String UPDATED_LOAN_EQUIPMENT = "BBBBBBBBBB";

    @Autowired
    private IncidentProgressRepository incidentProgressRepository;

    @Autowired
    private IncidentProgressMapper incidentProgressMapper;

    @Autowired
    private IncidentProgressService incidentProgressService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restIncidentProgressMockMvc;

    private IncidentProgress incidentProgress;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IncidentProgressResource incidentProgressResource = new IncidentProgressResource(incidentProgressService);
        this.restIncidentProgressMockMvc = MockMvcBuilders.standaloneSetup(incidentProgressResource)
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
    public static IncidentProgress createEntity() {
        IncidentProgress incidentProgress = new IncidentProgress()
            .incidentNumber(DEFAULT_INCIDENT_NUMBER)
            .progressNumber(DEFAULT_PROGRESS_NUMBER)
            .updatedBy(DEFAULT_UPDATED_BY)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .updatedPriority(DEFAULT_UPDATED_PRIORITY)
            .progressDescription(DEFAULT_PROGRESS_DESCRIPTION)
            .loanEquipment(DEFAULT_LOAN_EQUIPMENT);
        return incidentProgress;
    }

    @Before
    public void initTest() {
        incidentProgressRepository.deleteAll();
        incidentProgress = createEntity();
    }

    @Test
    public void createIncidentProgress() throws Exception {
        int databaseSizeBeforeCreate = incidentProgressRepository.findAll().size();

        // Create the IncidentProgress
        IncidentProgressDTO incidentProgressDTO = incidentProgressMapper.toDto(incidentProgress);
        restIncidentProgressMockMvc.perform(post("/api/incident-progresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentProgressDTO)))
            .andExpect(status().isCreated());

        // Validate the IncidentProgress in the database
        List<IncidentProgress> incidentProgressList = incidentProgressRepository.findAll();
        assertThat(incidentProgressList).hasSize(databaseSizeBeforeCreate + 1);
        IncidentProgress testIncidentProgress = incidentProgressList.get(incidentProgressList.size() - 1);
        assertThat(testIncidentProgress.getIncidentNumber()).isEqualTo(DEFAULT_INCIDENT_NUMBER);
        assertThat(testIncidentProgress.getProgressNumber()).isEqualTo(DEFAULT_PROGRESS_NUMBER);
        assertThat(testIncidentProgress.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testIncidentProgress.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testIncidentProgress.getUpdatedPriority()).isEqualTo(DEFAULT_UPDATED_PRIORITY);
        assertThat(testIncidentProgress.getProgressDescription()).isEqualTo(DEFAULT_PROGRESS_DESCRIPTION);
        assertThat(testIncidentProgress.getLoanEquipment()).isEqualTo(DEFAULT_LOAN_EQUIPMENT);
    }

    @Test
    public void createIncidentProgressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = incidentProgressRepository.findAll().size();

        // Create the IncidentProgress with an existing ID
        incidentProgress.setId("existing_id");
        IncidentProgressDTO incidentProgressDTO = incidentProgressMapper.toDto(incidentProgress);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIncidentProgressMockMvc.perform(post("/api/incident-progresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentProgressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IncidentProgress in the database
        List<IncidentProgress> incidentProgressList = incidentProgressRepository.findAll();
        assertThat(incidentProgressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkIncidentNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentProgressRepository.findAll().size();
        // set the field null
        incidentProgress.setIncidentNumber(null);

        // Create the IncidentProgress, which fails.
        IncidentProgressDTO incidentProgressDTO = incidentProgressMapper.toDto(incidentProgress);

        restIncidentProgressMockMvc.perform(post("/api/incident-progresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentProgressDTO)))
            .andExpect(status().isBadRequest());

        List<IncidentProgress> incidentProgressList = incidentProgressRepository.findAll();
        assertThat(incidentProgressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkProgressNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentProgressRepository.findAll().size();
        // set the field null
        incidentProgress.setProgressNumber(null);

        // Create the IncidentProgress, which fails.
        IncidentProgressDTO incidentProgressDTO = incidentProgressMapper.toDto(incidentProgress);

        restIncidentProgressMockMvc.perform(post("/api/incident-progresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentProgressDTO)))
            .andExpect(status().isBadRequest());

        List<IncidentProgress> incidentProgressList = incidentProgressRepository.findAll();
        assertThat(incidentProgressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkUpdatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentProgressRepository.findAll().size();
        // set the field null
        incidentProgress.setUpdatedBy(null);

        // Create the IncidentProgress, which fails.
        IncidentProgressDTO incidentProgressDTO = incidentProgressMapper.toDto(incidentProgress);

        restIncidentProgressMockMvc.perform(post("/api/incident-progresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentProgressDTO)))
            .andExpect(status().isBadRequest());

        List<IncidentProgress> incidentProgressList = incidentProgressRepository.findAll();
        assertThat(incidentProgressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDateUpdatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentProgressRepository.findAll().size();
        // set the field null
        incidentProgress.setDateUpdated(null);

        // Create the IncidentProgress, which fails.
        IncidentProgressDTO incidentProgressDTO = incidentProgressMapper.toDto(incidentProgress);

        restIncidentProgressMockMvc.perform(post("/api/incident-progresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentProgressDTO)))
            .andExpect(status().isBadRequest());

        List<IncidentProgress> incidentProgressList = incidentProgressRepository.findAll();
        assertThat(incidentProgressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkProgressDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentProgressRepository.findAll().size();
        // set the field null
        incidentProgress.setProgressDescription(null);

        // Create the IncidentProgress, which fails.
        IncidentProgressDTO incidentProgressDTO = incidentProgressMapper.toDto(incidentProgress);

        restIncidentProgressMockMvc.perform(post("/api/incident-progresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentProgressDTO)))
            .andExpect(status().isBadRequest());

        List<IncidentProgress> incidentProgressList = incidentProgressRepository.findAll();
        assertThat(incidentProgressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllIncidentProgresses() throws Exception {
        // Initialize the database
        incidentProgressRepository.save(incidentProgress);

        // Get all the incidentProgressList
        restIncidentProgressMockMvc.perform(get("/api/incident-progresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incidentProgress.getId())))
            .andExpect(jsonPath("$.[*].incidentNumber").value(hasItem(DEFAULT_INCIDENT_NUMBER)))
            .andExpect(jsonPath("$.[*].progressNumber").value(hasItem(DEFAULT_PROGRESS_NUMBER)))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(sameInstant(DEFAULT_DATE_UPDATED))))
            .andExpect(jsonPath("$.[*].updatedPriority").value(hasItem(DEFAULT_UPDATED_PRIORITY.toString())))
            .andExpect(jsonPath("$.[*].progressDescription").value(hasItem(DEFAULT_PROGRESS_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].loanEquipment").value(hasItem(DEFAULT_LOAN_EQUIPMENT.toString())));
    }

    @Test
    public void getIncidentProgress() throws Exception {
        // Initialize the database
        incidentProgressRepository.save(incidentProgress);

        // Get the incidentProgress
        restIncidentProgressMockMvc.perform(get("/api/incident-progresses/{id}", incidentProgress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(incidentProgress.getId()))
            .andExpect(jsonPath("$.incidentNumber").value(DEFAULT_INCIDENT_NUMBER))
            .andExpect(jsonPath("$.progressNumber").value(DEFAULT_PROGRESS_NUMBER))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(sameInstant(DEFAULT_DATE_UPDATED)))
            .andExpect(jsonPath("$.updatedPriority").value(DEFAULT_UPDATED_PRIORITY.toString()))
            .andExpect(jsonPath("$.progressDescription").value(DEFAULT_PROGRESS_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.loanEquipment").value(DEFAULT_LOAN_EQUIPMENT.toString()));
    }

    @Test
    public void getNonExistingIncidentProgress() throws Exception {
        // Get the incidentProgress
        restIncidentProgressMockMvc.perform(get("/api/incident-progresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateIncidentProgress() throws Exception {
        // Initialize the database
        incidentProgressRepository.save(incidentProgress);
        int databaseSizeBeforeUpdate = incidentProgressRepository.findAll().size();

        // Update the incidentProgress
        IncidentProgress updatedIncidentProgress = incidentProgressRepository.findOne(incidentProgress.getId());
        updatedIncidentProgress
            .incidentNumber(UPDATED_INCIDENT_NUMBER)
            .progressNumber(UPDATED_PROGRESS_NUMBER)
            .updatedBy(UPDATED_UPDATED_BY)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .updatedPriority(UPDATED_UPDATED_PRIORITY)
            .progressDescription(UPDATED_PROGRESS_DESCRIPTION)
            .loanEquipment(UPDATED_LOAN_EQUIPMENT);
        IncidentProgressDTO incidentProgressDTO = incidentProgressMapper.toDto(updatedIncidentProgress);

        restIncidentProgressMockMvc.perform(put("/api/incident-progresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentProgressDTO)))
            .andExpect(status().isOk());

        // Validate the IncidentProgress in the database
        List<IncidentProgress> incidentProgressList = incidentProgressRepository.findAll();
        assertThat(incidentProgressList).hasSize(databaseSizeBeforeUpdate);
        IncidentProgress testIncidentProgress = incidentProgressList.get(incidentProgressList.size() - 1);
        assertThat(testIncidentProgress.getIncidentNumber()).isEqualTo(UPDATED_INCIDENT_NUMBER);
        assertThat(testIncidentProgress.getProgressNumber()).isEqualTo(UPDATED_PROGRESS_NUMBER);
        assertThat(testIncidentProgress.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testIncidentProgress.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testIncidentProgress.getUpdatedPriority()).isEqualTo(UPDATED_UPDATED_PRIORITY);
        assertThat(testIncidentProgress.getProgressDescription()).isEqualTo(UPDATED_PROGRESS_DESCRIPTION);
        assertThat(testIncidentProgress.getLoanEquipment()).isEqualTo(UPDATED_LOAN_EQUIPMENT);
    }

    @Test
    public void updateNonExistingIncidentProgress() throws Exception {
        int databaseSizeBeforeUpdate = incidentProgressRepository.findAll().size();

        // Create the IncidentProgress
        IncidentProgressDTO incidentProgressDTO = incidentProgressMapper.toDto(incidentProgress);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIncidentProgressMockMvc.perform(put("/api/incident-progresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentProgressDTO)))
            .andExpect(status().isCreated());

        // Validate the IncidentProgress in the database
        List<IncidentProgress> incidentProgressList = incidentProgressRepository.findAll();
        assertThat(incidentProgressList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteIncidentProgress() throws Exception {
        // Initialize the database
        incidentProgressRepository.save(incidentProgress);
        int databaseSizeBeforeDelete = incidentProgressRepository.findAll().size();

        // Get the incidentProgress
        restIncidentProgressMockMvc.perform(delete("/api/incident-progresses/{id}", incidentProgress.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<IncidentProgress> incidentProgressList = incidentProgressRepository.findAll();
        assertThat(incidentProgressList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncidentProgress.class);
        IncidentProgress incidentProgress1 = new IncidentProgress();
        incidentProgress1.setId("id1");
        IncidentProgress incidentProgress2 = new IncidentProgress();
        incidentProgress2.setId(incidentProgress1.getId());
        assertThat(incidentProgress1).isEqualTo(incidentProgress2);
        incidentProgress2.setId("id2");
        assertThat(incidentProgress1).isNotEqualTo(incidentProgress2);
        incidentProgress1.setId(null);
        assertThat(incidentProgress1).isNotEqualTo(incidentProgress2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncidentProgressDTO.class);
        IncidentProgressDTO incidentProgressDTO1 = new IncidentProgressDTO();
        incidentProgressDTO1.setId("id1");
        IncidentProgressDTO incidentProgressDTO2 = new IncidentProgressDTO();
        assertThat(incidentProgressDTO1).isNotEqualTo(incidentProgressDTO2);
        incidentProgressDTO2.setId(incidentProgressDTO1.getId());
        assertThat(incidentProgressDTO1).isEqualTo(incidentProgressDTO2);
        incidentProgressDTO2.setId("id2");
        assertThat(incidentProgressDTO1).isNotEqualTo(incidentProgressDTO2);
        incidentProgressDTO1.setId(null);
        assertThat(incidentProgressDTO1).isNotEqualTo(incidentProgressDTO2);
    }
}
