package za.co.ajk.incidentman.web.rest;

import za.co.ajk.incidentman.EventModuleApp;

import za.co.ajk.incidentman.domain.EquipmentTracking;
import za.co.ajk.incidentman.repository.EquipmentTrackingRepository;
import za.co.ajk.incidentman.service.EquipmentTrackingService;
import za.co.ajk.incidentman.service.dto.EquipmentTrackingDTO;
import za.co.ajk.incidentman.service.mapper.EquipmentTrackingMapper;
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

/**
 * Test class for the EquipmentTrackingResource REST controller.
 *
 * @see EquipmentTrackingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EventModuleApp.class)
public class EquipmentTrackingResourceIntTest {

    private static final String DEFAULT_EQUIPMENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_EQUIPMENT_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_TRACKING_ID = 1;
    private static final Integer UPDATED_TRACKING_ID = 2;

    private static final ZonedDateTime DEFAULT_DATE_ON_LOAN = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_ON_LOAN = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_BOOKED_OUT_BY = "AAAAAAAAAA";
    private static final String UPDATED_BOOKED_OUT_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_BOOKED_BACK = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_BOOKED_BACK = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_BOOKED_IN_BY = "AAAAAAAAAA";
    private static final String UPDATED_BOOKED_IN_BY = "BBBBBBBBBB";

    @Autowired
    private EquipmentTrackingRepository equipmentTrackingRepository;

    @Autowired
    private EquipmentTrackingMapper equipmentTrackingMapper;

    @Autowired
    private EquipmentTrackingService equipmentTrackingService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restEquipmentTrackingMockMvc;

    private EquipmentTracking equipmentTracking;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EquipmentTrackingResource equipmentTrackingResource = new EquipmentTrackingResource(equipmentTrackingService);
        this.restEquipmentTrackingMockMvc = MockMvcBuilders.standaloneSetup(equipmentTrackingResource)
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
    public static EquipmentTracking createEntity() {
        EquipmentTracking equipmentTracking = new EquipmentTracking()
            .equipmentId(DEFAULT_EQUIPMENT_ID)
            .trackingId(DEFAULT_TRACKING_ID)
            .dateOnLoan(DEFAULT_DATE_ON_LOAN)
            .bookedOutBy(DEFAULT_BOOKED_OUT_BY)
            .dateBookedBack(DEFAULT_DATE_BOOKED_BACK)
            .bookedInBy(DEFAULT_BOOKED_IN_BY);
        return equipmentTracking;
    }

    @Before
    public void initTest() {
        equipmentTrackingRepository.deleteAll();
        equipmentTracking = createEntity();
    }

    @Test
    public void createEquipmentTracking() throws Exception {
        int databaseSizeBeforeCreate = equipmentTrackingRepository.findAll().size();

        // Create the EquipmentTracking
        EquipmentTrackingDTO equipmentTrackingDTO = equipmentTrackingMapper.toDto(equipmentTracking);
        restEquipmentTrackingMockMvc.perform(post("/api/equipment-trackings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipmentTrackingDTO)))
            .andExpect(status().isCreated());

        // Validate the EquipmentTracking in the database
        List<EquipmentTracking> equipmentTrackingList = equipmentTrackingRepository.findAll();
        assertThat(equipmentTrackingList).hasSize(databaseSizeBeforeCreate + 1);
        EquipmentTracking testEquipmentTracking = equipmentTrackingList.get(equipmentTrackingList.size() - 1);
        assertThat(testEquipmentTracking.getEquipmentId()).isEqualTo(DEFAULT_EQUIPMENT_ID);
        assertThat(testEquipmentTracking.getTrackingId()).isEqualTo(DEFAULT_TRACKING_ID);
        assertThat(testEquipmentTracking.getDateOnLoan()).isEqualTo(DEFAULT_DATE_ON_LOAN);
        assertThat(testEquipmentTracking.getBookedOutBy()).isEqualTo(DEFAULT_BOOKED_OUT_BY);
        assertThat(testEquipmentTracking.getDateBookedBack()).isEqualTo(DEFAULT_DATE_BOOKED_BACK);
        assertThat(testEquipmentTracking.getBookedInBy()).isEqualTo(DEFAULT_BOOKED_IN_BY);
    }

    @Test
    public void createEquipmentTrackingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = equipmentTrackingRepository.findAll().size();

        // Create the EquipmentTracking with an existing ID
        equipmentTracking.setId("existing_id");
        EquipmentTrackingDTO equipmentTrackingDTO = equipmentTrackingMapper.toDto(equipmentTracking);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEquipmentTrackingMockMvc.perform(post("/api/equipment-trackings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipmentTrackingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EquipmentTracking in the database
        List<EquipmentTracking> equipmentTrackingList = equipmentTrackingRepository.findAll();
        assertThat(equipmentTrackingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkEquipmentIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipmentTrackingRepository.findAll().size();
        // set the field null
        equipmentTracking.setEquipmentId(null);

        // Create the EquipmentTracking, which fails.
        EquipmentTrackingDTO equipmentTrackingDTO = equipmentTrackingMapper.toDto(equipmentTracking);

        restEquipmentTrackingMockMvc.perform(post("/api/equipment-trackings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipmentTrackingDTO)))
            .andExpect(status().isBadRequest());

        List<EquipmentTracking> equipmentTrackingList = equipmentTrackingRepository.findAll();
        assertThat(equipmentTrackingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkTrackingIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipmentTrackingRepository.findAll().size();
        // set the field null
        equipmentTracking.setTrackingId(null);

        // Create the EquipmentTracking, which fails.
        EquipmentTrackingDTO equipmentTrackingDTO = equipmentTrackingMapper.toDto(equipmentTracking);

        restEquipmentTrackingMockMvc.perform(post("/api/equipment-trackings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipmentTrackingDTO)))
            .andExpect(status().isBadRequest());

        List<EquipmentTracking> equipmentTrackingList = equipmentTrackingRepository.findAll();
        assertThat(equipmentTrackingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDateOnLoanIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipmentTrackingRepository.findAll().size();
        // set the field null
        equipmentTracking.setDateOnLoan(null);

        // Create the EquipmentTracking, which fails.
        EquipmentTrackingDTO equipmentTrackingDTO = equipmentTrackingMapper.toDto(equipmentTracking);

        restEquipmentTrackingMockMvc.perform(post("/api/equipment-trackings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipmentTrackingDTO)))
            .andExpect(status().isBadRequest());

        List<EquipmentTracking> equipmentTrackingList = equipmentTrackingRepository.findAll();
        assertThat(equipmentTrackingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkBookedOutByIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipmentTrackingRepository.findAll().size();
        // set the field null
        equipmentTracking.setBookedOutBy(null);

        // Create the EquipmentTracking, which fails.
        EquipmentTrackingDTO equipmentTrackingDTO = equipmentTrackingMapper.toDto(equipmentTracking);

        restEquipmentTrackingMockMvc.perform(post("/api/equipment-trackings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipmentTrackingDTO)))
            .andExpect(status().isBadRequest());

        List<EquipmentTracking> equipmentTrackingList = equipmentTrackingRepository.findAll();
        assertThat(equipmentTrackingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllEquipmentTrackings() throws Exception {
        // Initialize the database
        equipmentTrackingRepository.save(equipmentTracking);

        // Get all the equipmentTrackingList
        restEquipmentTrackingMockMvc.perform(get("/api/equipment-trackings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equipmentTracking.getId())))
            .andExpect(jsonPath("$.[*].equipmentId").value(hasItem(DEFAULT_EQUIPMENT_ID.toString())))
            .andExpect(jsonPath("$.[*].trackingId").value(hasItem(DEFAULT_TRACKING_ID)))
            .andExpect(jsonPath("$.[*].dateOnLoan").value(hasItem(sameInstant(DEFAULT_DATE_ON_LOAN))))
            .andExpect(jsonPath("$.[*].bookedOutBy").value(hasItem(DEFAULT_BOOKED_OUT_BY.toString())))
            .andExpect(jsonPath("$.[*].dateBookedBack").value(hasItem(sameInstant(DEFAULT_DATE_BOOKED_BACK))))
            .andExpect(jsonPath("$.[*].bookedInBy").value(hasItem(DEFAULT_BOOKED_IN_BY.toString())));
    }

    @Test
    public void getEquipmentTracking() throws Exception {
        // Initialize the database
        equipmentTrackingRepository.save(equipmentTracking);

        // Get the equipmentTracking
        restEquipmentTrackingMockMvc.perform(get("/api/equipment-trackings/{id}", equipmentTracking.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(equipmentTracking.getId()))
            .andExpect(jsonPath("$.equipmentId").value(DEFAULT_EQUIPMENT_ID.toString()))
            .andExpect(jsonPath("$.trackingId").value(DEFAULT_TRACKING_ID))
            .andExpect(jsonPath("$.dateOnLoan").value(sameInstant(DEFAULT_DATE_ON_LOAN)))
            .andExpect(jsonPath("$.bookedOutBy").value(DEFAULT_BOOKED_OUT_BY.toString()))
            .andExpect(jsonPath("$.dateBookedBack").value(sameInstant(DEFAULT_DATE_BOOKED_BACK)))
            .andExpect(jsonPath("$.bookedInBy").value(DEFAULT_BOOKED_IN_BY.toString()));
    }

    @Test
    public void getNonExistingEquipmentTracking() throws Exception {
        // Get the equipmentTracking
        restEquipmentTrackingMockMvc.perform(get("/api/equipment-trackings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateEquipmentTracking() throws Exception {
        // Initialize the database
        equipmentTrackingRepository.save(equipmentTracking);
        int databaseSizeBeforeUpdate = equipmentTrackingRepository.findAll().size();

        // Update the equipmentTracking
        EquipmentTracking updatedEquipmentTracking = equipmentTrackingRepository.findOne(equipmentTracking.getId());
        updatedEquipmentTracking
            .equipmentId(UPDATED_EQUIPMENT_ID)
            .trackingId(UPDATED_TRACKING_ID)
            .dateOnLoan(UPDATED_DATE_ON_LOAN)
            .bookedOutBy(UPDATED_BOOKED_OUT_BY)
            .dateBookedBack(UPDATED_DATE_BOOKED_BACK)
            .bookedInBy(UPDATED_BOOKED_IN_BY);
        EquipmentTrackingDTO equipmentTrackingDTO = equipmentTrackingMapper.toDto(updatedEquipmentTracking);

        restEquipmentTrackingMockMvc.perform(put("/api/equipment-trackings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipmentTrackingDTO)))
            .andExpect(status().isOk());

        // Validate the EquipmentTracking in the database
        List<EquipmentTracking> equipmentTrackingList = equipmentTrackingRepository.findAll();
        assertThat(equipmentTrackingList).hasSize(databaseSizeBeforeUpdate);
        EquipmentTracking testEquipmentTracking = equipmentTrackingList.get(equipmentTrackingList.size() - 1);
        assertThat(testEquipmentTracking.getEquipmentId()).isEqualTo(UPDATED_EQUIPMENT_ID);
        assertThat(testEquipmentTracking.getTrackingId()).isEqualTo(UPDATED_TRACKING_ID);
        assertThat(testEquipmentTracking.getDateOnLoan()).isEqualTo(UPDATED_DATE_ON_LOAN);
        assertThat(testEquipmentTracking.getBookedOutBy()).isEqualTo(UPDATED_BOOKED_OUT_BY);
        assertThat(testEquipmentTracking.getDateBookedBack()).isEqualTo(UPDATED_DATE_BOOKED_BACK);
        assertThat(testEquipmentTracking.getBookedInBy()).isEqualTo(UPDATED_BOOKED_IN_BY);
    }

    @Test
    public void updateNonExistingEquipmentTracking() throws Exception {
        int databaseSizeBeforeUpdate = equipmentTrackingRepository.findAll().size();

        // Create the EquipmentTracking
        EquipmentTrackingDTO equipmentTrackingDTO = equipmentTrackingMapper.toDto(equipmentTracking);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEquipmentTrackingMockMvc.perform(put("/api/equipment-trackings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipmentTrackingDTO)))
            .andExpect(status().isCreated());

        // Validate the EquipmentTracking in the database
        List<EquipmentTracking> equipmentTrackingList = equipmentTrackingRepository.findAll();
        assertThat(equipmentTrackingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteEquipmentTracking() throws Exception {
        // Initialize the database
        equipmentTrackingRepository.save(equipmentTracking);
        int databaseSizeBeforeDelete = equipmentTrackingRepository.findAll().size();

        // Get the equipmentTracking
        restEquipmentTrackingMockMvc.perform(delete("/api/equipment-trackings/{id}", equipmentTracking.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EquipmentTracking> equipmentTrackingList = equipmentTrackingRepository.findAll();
        assertThat(equipmentTrackingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EquipmentTracking.class);
        EquipmentTracking equipmentTracking1 = new EquipmentTracking();
        equipmentTracking1.setId("id1");
        EquipmentTracking equipmentTracking2 = new EquipmentTracking();
        equipmentTracking2.setId(equipmentTracking1.getId());
        assertThat(equipmentTracking1).isEqualTo(equipmentTracking2);
        equipmentTracking2.setId("id2");
        assertThat(equipmentTracking1).isNotEqualTo(equipmentTracking2);
        equipmentTracking1.setId(null);
        assertThat(equipmentTracking1).isNotEqualTo(equipmentTracking2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EquipmentTrackingDTO.class);
        EquipmentTrackingDTO equipmentTrackingDTO1 = new EquipmentTrackingDTO();
        equipmentTrackingDTO1.setId("id1");
        EquipmentTrackingDTO equipmentTrackingDTO2 = new EquipmentTrackingDTO();
        assertThat(equipmentTrackingDTO1).isNotEqualTo(equipmentTrackingDTO2);
        equipmentTrackingDTO2.setId(equipmentTrackingDTO1.getId());
        assertThat(equipmentTrackingDTO1).isEqualTo(equipmentTrackingDTO2);
        equipmentTrackingDTO2.setId("id2");
        assertThat(equipmentTrackingDTO1).isNotEqualTo(equipmentTrackingDTO2);
        equipmentTrackingDTO1.setId(null);
        assertThat(equipmentTrackingDTO1).isNotEqualTo(equipmentTrackingDTO2);
    }
}
