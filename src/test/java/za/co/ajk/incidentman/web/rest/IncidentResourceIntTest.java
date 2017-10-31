package za.co.ajk.incidentman.web.rest;

import za.co.ajk.incidentman.EventModuleApp;

import za.co.ajk.incidentman.domain.Incident;
import za.co.ajk.incidentman.repository.IncidentRepository;
import za.co.ajk.incidentman.service.IncidentService;
import za.co.ajk.incidentman.service.dto.IncidentDTO;
import za.co.ajk.incidentman.service.mapper.IncidentMapper;
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
import org.springframework.util.Base64Utils;

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
import za.co.ajk.incidentman.domain.enumeration.IncidentStatus;
/**
 * Test class for the IncidentResource REST controller.
 *
 * @see IncidentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EventModuleApp.class)
public class IncidentResourceIntTest {

    private static final String DEFAULT_COMPANY_KEY = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_KEY = "BBBBBBBBBB";

    private static final Integer DEFAULT_INCIDENT_NUMBER = 1;
    private static final Integer UPDATED_INCIDENT_NUMBER = 2;

    private static final String DEFAULT_LOGGED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LOGGED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_LOGGED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_LOGGED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Priority DEFAULT_PRIORITY = Priority.LOW;
    private static final Priority UPDATED_PRIORITY = Priority.MEDIUM;

    private static final IncidentStatus DEFAULT_INCIDENT_STATUS = IncidentStatus.OPEN;
    private static final IncidentStatus UPDATED_INCIDENT_STATUS = IncidentStatus.PENDING_PARTS;

    private static final String DEFAULT_INCIDENT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_INCIDENT_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_SCREEN_CAPTURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SCREEN_CAPTURE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_SCREEN_CAPTURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SCREEN_CAPTURE_CONTENT_TYPE = "image/png";

    @Autowired
    private IncidentRepository incidentRepository;

    @Autowired
    private IncidentMapper incidentMapper;

    @Autowired
    private IncidentService incidentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restIncidentMockMvc;

    private Incident incident;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IncidentResource incidentResource = new IncidentResource(incidentService);
        this.restIncidentMockMvc = MockMvcBuilders.standaloneSetup(incidentResource)
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
    public static Incident createEntity() {
        Incident incident = new Incident()
            .companyKey(DEFAULT_COMPANY_KEY)
            .incidentNumber(DEFAULT_INCIDENT_NUMBER)
            .loggedBy(DEFAULT_LOGGED_BY)
            .dateLogged(DEFAULT_DATE_LOGGED)
            .priority(DEFAULT_PRIORITY)
            .incidentStatus(DEFAULT_INCIDENT_STATUS)
            .incidentDescription(DEFAULT_INCIDENT_DESCRIPTION)
            .screenCapture(DEFAULT_SCREEN_CAPTURE)
            .screenCaptureContentType(DEFAULT_SCREEN_CAPTURE_CONTENT_TYPE);
        return incident;
    }

    @Before
    public void initTest() {
        incidentRepository.deleteAll();
        incident = createEntity();
    }

    @Test
    public void createIncident() throws Exception {
        int databaseSizeBeforeCreate = incidentRepository.findAll().size();

        // Create the Incident
        IncidentDTO incidentDTO = incidentMapper.toDto(incident);
        restIncidentMockMvc.perform(post("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentDTO)))
            .andExpect(status().isCreated());

        // Validate the Incident in the database
        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeCreate + 1);
        Incident testIncident = incidentList.get(incidentList.size() - 1);
        assertThat(testIncident.getCompanyKey()).isEqualTo(DEFAULT_COMPANY_KEY);
        assertThat(testIncident.getIncidentNumber()).isEqualTo(DEFAULT_INCIDENT_NUMBER);
        assertThat(testIncident.getLoggedBy()).isEqualTo(DEFAULT_LOGGED_BY);
        assertThat(testIncident.getDateLogged()).isEqualTo(DEFAULT_DATE_LOGGED);
        assertThat(testIncident.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testIncident.getIncidentStatus()).isEqualTo(DEFAULT_INCIDENT_STATUS);
        assertThat(testIncident.getIncidentDescription()).isEqualTo(DEFAULT_INCIDENT_DESCRIPTION);
        assertThat(testIncident.getScreenCapture()).isEqualTo(DEFAULT_SCREEN_CAPTURE);
        assertThat(testIncident.getScreenCaptureContentType()).isEqualTo(DEFAULT_SCREEN_CAPTURE_CONTENT_TYPE);
    }

    @Test
    public void createIncidentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = incidentRepository.findAll().size();

        // Create the Incident with an existing ID
        incident.setId("existing_id");
        IncidentDTO incidentDTO = incidentMapper.toDto(incident);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIncidentMockMvc.perform(post("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Incident in the database
        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkCompanyKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentRepository.findAll().size();
        // set the field null
        incident.setCompanyKey(null);

        // Create the Incident, which fails.
        IncidentDTO incidentDTO = incidentMapper.toDto(incident);

        restIncidentMockMvc.perform(post("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentDTO)))
            .andExpect(status().isBadRequest());

        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkIncidentNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentRepository.findAll().size();
        // set the field null
        incident.setIncidentNumber(null);

        // Create the Incident, which fails.
        IncidentDTO incidentDTO = incidentMapper.toDto(incident);

        restIncidentMockMvc.perform(post("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentDTO)))
            .andExpect(status().isBadRequest());

        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkLoggedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentRepository.findAll().size();
        // set the field null
        incident.setLoggedBy(null);

        // Create the Incident, which fails.
        IncidentDTO incidentDTO = incidentMapper.toDto(incident);

        restIncidentMockMvc.perform(post("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentDTO)))
            .andExpect(status().isBadRequest());

        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDateLoggedIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentRepository.findAll().size();
        // set the field null
        incident.setDateLogged(null);

        // Create the Incident, which fails.
        IncidentDTO incidentDTO = incidentMapper.toDto(incident);

        restIncidentMockMvc.perform(post("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentDTO)))
            .andExpect(status().isBadRequest());

        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkPriorityIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentRepository.findAll().size();
        // set the field null
        incident.setPriority(null);

        // Create the Incident, which fails.
        IncidentDTO incidentDTO = incidentMapper.toDto(incident);

        restIncidentMockMvc.perform(post("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentDTO)))
            .andExpect(status().isBadRequest());

        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkIncidentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentRepository.findAll().size();
        // set the field null
        incident.setIncidentStatus(null);

        // Create the Incident, which fails.
        IncidentDTO incidentDTO = incidentMapper.toDto(incident);

        restIncidentMockMvc.perform(post("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentDTO)))
            .andExpect(status().isBadRequest());

        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkIncidentDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentRepository.findAll().size();
        // set the field null
        incident.setIncidentDescription(null);

        // Create the Incident, which fails.
        IncidentDTO incidentDTO = incidentMapper.toDto(incident);

        restIncidentMockMvc.perform(post("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentDTO)))
            .andExpect(status().isBadRequest());

        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllIncidents() throws Exception {
        // Initialize the database
        incidentRepository.save(incident);

        // Get all the incidentList
        restIncidentMockMvc.perform(get("/api/incidents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incident.getId())))
            .andExpect(jsonPath("$.[*].companyKey").value(hasItem(DEFAULT_COMPANY_KEY.toString())))
            .andExpect(jsonPath("$.[*].incidentNumber").value(hasItem(DEFAULT_INCIDENT_NUMBER)))
            .andExpect(jsonPath("$.[*].loggedBy").value(hasItem(DEFAULT_LOGGED_BY.toString())))
            .andExpect(jsonPath("$.[*].dateLogged").value(hasItem(sameInstant(DEFAULT_DATE_LOGGED))))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.toString())))
            .andExpect(jsonPath("$.[*].incidentStatus").value(hasItem(DEFAULT_INCIDENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].incidentDescription").value(hasItem(DEFAULT_INCIDENT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].screenCaptureContentType").value(hasItem(DEFAULT_SCREEN_CAPTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].screenCapture").value(hasItem(Base64Utils.encodeToString(DEFAULT_SCREEN_CAPTURE))));
    }

    @Test
    public void getIncident() throws Exception {
        // Initialize the database
        incidentRepository.save(incident);

        // Get the incident
        restIncidentMockMvc.perform(get("/api/incidents/{id}", incident.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(incident.getId()))
            .andExpect(jsonPath("$.companyKey").value(DEFAULT_COMPANY_KEY.toString()))
            .andExpect(jsonPath("$.incidentNumber").value(DEFAULT_INCIDENT_NUMBER))
            .andExpect(jsonPath("$.loggedBy").value(DEFAULT_LOGGED_BY.toString()))
            .andExpect(jsonPath("$.dateLogged").value(sameInstant(DEFAULT_DATE_LOGGED)))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY.toString()))
            .andExpect(jsonPath("$.incidentStatus").value(DEFAULT_INCIDENT_STATUS.toString()))
            .andExpect(jsonPath("$.incidentDescription").value(DEFAULT_INCIDENT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.screenCaptureContentType").value(DEFAULT_SCREEN_CAPTURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.screenCapture").value(Base64Utils.encodeToString(DEFAULT_SCREEN_CAPTURE)));
    }

    @Test
    public void getNonExistingIncident() throws Exception {
        // Get the incident
        restIncidentMockMvc.perform(get("/api/incidents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateIncident() throws Exception {
        // Initialize the database
        incidentRepository.save(incident);
        int databaseSizeBeforeUpdate = incidentRepository.findAll().size();

        // Update the incident
        Incident updatedIncident = incidentRepository.findOne(incident.getId());
        updatedIncident
            .companyKey(UPDATED_COMPANY_KEY)
            .incidentNumber(UPDATED_INCIDENT_NUMBER)
            .loggedBy(UPDATED_LOGGED_BY)
            .dateLogged(UPDATED_DATE_LOGGED)
            .priority(UPDATED_PRIORITY)
            .incidentStatus(UPDATED_INCIDENT_STATUS)
            .incidentDescription(UPDATED_INCIDENT_DESCRIPTION)
            .screenCapture(UPDATED_SCREEN_CAPTURE)
            .screenCaptureContentType(UPDATED_SCREEN_CAPTURE_CONTENT_TYPE);
        IncidentDTO incidentDTO = incidentMapper.toDto(updatedIncident);

        restIncidentMockMvc.perform(put("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentDTO)))
            .andExpect(status().isOk());

        // Validate the Incident in the database
        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeUpdate);
        Incident testIncident = incidentList.get(incidentList.size() - 1);
        assertThat(testIncident.getCompanyKey()).isEqualTo(UPDATED_COMPANY_KEY);
        assertThat(testIncident.getIncidentNumber()).isEqualTo(UPDATED_INCIDENT_NUMBER);
        assertThat(testIncident.getLoggedBy()).isEqualTo(UPDATED_LOGGED_BY);
        assertThat(testIncident.getDateLogged()).isEqualTo(UPDATED_DATE_LOGGED);
        assertThat(testIncident.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testIncident.getIncidentStatus()).isEqualTo(UPDATED_INCIDENT_STATUS);
        assertThat(testIncident.getIncidentDescription()).isEqualTo(UPDATED_INCIDENT_DESCRIPTION);
        assertThat(testIncident.getScreenCapture()).isEqualTo(UPDATED_SCREEN_CAPTURE);
        assertThat(testIncident.getScreenCaptureContentType()).isEqualTo(UPDATED_SCREEN_CAPTURE_CONTENT_TYPE);
    }

    @Test
    public void updateNonExistingIncident() throws Exception {
        int databaseSizeBeforeUpdate = incidentRepository.findAll().size();

        // Create the Incident
        IncidentDTO incidentDTO = incidentMapper.toDto(incident);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIncidentMockMvc.perform(put("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentDTO)))
            .andExpect(status().isCreated());

        // Validate the Incident in the database
        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteIncident() throws Exception {
        // Initialize the database
        incidentRepository.save(incident);
        int databaseSizeBeforeDelete = incidentRepository.findAll().size();

        // Get the incident
        restIncidentMockMvc.perform(delete("/api/incidents/{id}", incident.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Incident.class);
        Incident incident1 = new Incident();
        incident1.setId("id1");
        Incident incident2 = new Incident();
        incident2.setId(incident1.getId());
        assertThat(incident1).isEqualTo(incident2);
        incident2.setId("id2");
        assertThat(incident1).isNotEqualTo(incident2);
        incident1.setId(null);
        assertThat(incident1).isNotEqualTo(incident2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncidentDTO.class);
        IncidentDTO incidentDTO1 = new IncidentDTO();
        incidentDTO1.setId("id1");
        IncidentDTO incidentDTO2 = new IncidentDTO();
        assertThat(incidentDTO1).isNotEqualTo(incidentDTO2);
        incidentDTO2.setId(incidentDTO1.getId());
        assertThat(incidentDTO1).isEqualTo(incidentDTO2);
        incidentDTO2.setId("id2");
        assertThat(incidentDTO1).isNotEqualTo(incidentDTO2);
        incidentDTO1.setId(null);
        assertThat(incidentDTO1).isNotEqualTo(incidentDTO2);
    }
}
