package za.co.ajk.incidentman.web.rest;

import za.co.ajk.incidentman.EventModuleApp;

import za.co.ajk.incidentman.domain.Equipment;
import za.co.ajk.incidentman.repository.EquipmentRepository;
import za.co.ajk.incidentman.service.EquipmentService;
import za.co.ajk.incidentman.service.dto.EquipmentDTO;
import za.co.ajk.incidentman.service.mapper.EquipmentMapper;
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

import za.co.ajk.incidentman.domain.enumeration.EquipmentStatus;
/**
 * Test class for the EquipmentResource REST controller.
 *
 * @see EquipmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EventModuleApp.class)
public class EquipmentResourceIntTest {

    private static final String DEFAULT_EQUIPMENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_EQUIPMENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EQUIPMENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EQUIPMENT_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_LOADED_ON_SYSTEM = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_LOADED_ON_SYSTEM = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_UPLOADED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPLOADED_BY = "BBBBBBBBBB";

    private static final EquipmentStatus DEFAULT_CURRENT_STATUS = EquipmentStatus.AVAILABLE;
    private static final EquipmentStatus UPDATED_CURRENT_STATUS = EquipmentStatus.ON_LOAN;

    private static final ZonedDateTime DEFAULT_DATE_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATE_MODIFIED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_MODIFIED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private EquipmentMapper equipmentMapper;

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restEquipmentMockMvc;

    private Equipment equipment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EquipmentResource equipmentResource = new EquipmentResource(equipmentService);
        this.restEquipmentMockMvc = MockMvcBuilders.standaloneSetup(equipmentResource)
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
    public static Equipment createEntity() {
        Equipment equipment = new Equipment()
            .equipmentId(DEFAULT_EQUIPMENT_ID)
            .equipmentName(DEFAULT_EQUIPMENT_NAME)
            .dateLoadedOnSystem(DEFAULT_DATE_LOADED_ON_SYSTEM)
            .uploadedBy(DEFAULT_UPLOADED_BY)
            .currentStatus(DEFAULT_CURRENT_STATUS)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateModified(DEFAULT_DATE_MODIFIED)
            .updatedBy(DEFAULT_UPDATED_BY);
        return equipment;
    }

    @Before
    public void initTest() {
        equipmentRepository.deleteAll();
        equipment = createEntity();
    }

    @Test
    public void createEquipment() throws Exception {
        int databaseSizeBeforeCreate = equipmentRepository.findAll().size();

        // Create the Equipment
        EquipmentDTO equipmentDTO = equipmentMapper.toDto(equipment);
        restEquipmentMockMvc.perform(post("/api/equipment")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Equipment in the database
        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeCreate + 1);
        Equipment testEquipment = equipmentList.get(equipmentList.size() - 1);
        assertThat(testEquipment.getEquipmentId()).isEqualTo(DEFAULT_EQUIPMENT_ID);
        assertThat(testEquipment.getEquipmentName()).isEqualTo(DEFAULT_EQUIPMENT_NAME);
        assertThat(testEquipment.getDateLoadedOnSystem()).isEqualTo(DEFAULT_DATE_LOADED_ON_SYSTEM);
        assertThat(testEquipment.getUploadedBy()).isEqualTo(DEFAULT_UPLOADED_BY);
        assertThat(testEquipment.getCurrentStatus()).isEqualTo(DEFAULT_CURRENT_STATUS);
        assertThat(testEquipment.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testEquipment.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
        assertThat(testEquipment.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    public void createEquipmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = equipmentRepository.findAll().size();

        // Create the Equipment with an existing ID
        equipment.setId("existing_id");
        EquipmentDTO equipmentDTO = equipmentMapper.toDto(equipment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEquipmentMockMvc.perform(post("/api/equipment")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Equipment in the database
        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkEquipmentIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipmentRepository.findAll().size();
        // set the field null
        equipment.setEquipmentId(null);

        // Create the Equipment, which fails.
        EquipmentDTO equipmentDTO = equipmentMapper.toDto(equipment);

        restEquipmentMockMvc.perform(post("/api/equipment")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipmentDTO)))
            .andExpect(status().isBadRequest());

        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEquipmentNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipmentRepository.findAll().size();
        // set the field null
        equipment.setEquipmentName(null);

        // Create the Equipment, which fails.
        EquipmentDTO equipmentDTO = equipmentMapper.toDto(equipment);

        restEquipmentMockMvc.perform(post("/api/equipment")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipmentDTO)))
            .andExpect(status().isBadRequest());

        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDateLoadedOnSystemIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipmentRepository.findAll().size();
        // set the field null
        equipment.setDateLoadedOnSystem(null);

        // Create the Equipment, which fails.
        EquipmentDTO equipmentDTO = equipmentMapper.toDto(equipment);

        restEquipmentMockMvc.perform(post("/api/equipment")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipmentDTO)))
            .andExpect(status().isBadRequest());

        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkUploadedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipmentRepository.findAll().size();
        // set the field null
        equipment.setUploadedBy(null);

        // Create the Equipment, which fails.
        EquipmentDTO equipmentDTO = equipmentMapper.toDto(equipment);

        restEquipmentMockMvc.perform(post("/api/equipment")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipmentDTO)))
            .andExpect(status().isBadRequest());

        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCurrentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipmentRepository.findAll().size();
        // set the field null
        equipment.setCurrentStatus(null);

        // Create the Equipment, which fails.
        EquipmentDTO equipmentDTO = equipmentMapper.toDto(equipment);

        restEquipmentMockMvc.perform(post("/api/equipment")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipmentDTO)))
            .andExpect(status().isBadRequest());

        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDateCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipmentRepository.findAll().size();
        // set the field null
        equipment.setDateCreated(null);

        // Create the Equipment, which fails.
        EquipmentDTO equipmentDTO = equipmentMapper.toDto(equipment);

        restEquipmentMockMvc.perform(post("/api/equipment")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipmentDTO)))
            .andExpect(status().isBadRequest());

        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDateModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipmentRepository.findAll().size();
        // set the field null
        equipment.setDateModified(null);

        // Create the Equipment, which fails.
        EquipmentDTO equipmentDTO = equipmentMapper.toDto(equipment);

        restEquipmentMockMvc.perform(post("/api/equipment")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipmentDTO)))
            .andExpect(status().isBadRequest());

        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllEquipment() throws Exception {
        // Initialize the database
        equipmentRepository.save(equipment);

        // Get all the equipmentList
        restEquipmentMockMvc.perform(get("/api/equipment?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equipment.getId())))
            .andExpect(jsonPath("$.[*].equipmentId").value(hasItem(DEFAULT_EQUIPMENT_ID.toString())))
            .andExpect(jsonPath("$.[*].equipmentName").value(hasItem(DEFAULT_EQUIPMENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].dateLoadedOnSystem").value(hasItem(sameInstant(DEFAULT_DATE_LOADED_ON_SYSTEM))))
            .andExpect(jsonPath("$.[*].uploadedBy").value(hasItem(DEFAULT_UPLOADED_BY.toString())))
            .andExpect(jsonPath("$.[*].currentStatus").value(hasItem(DEFAULT_CURRENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(sameInstant(DEFAULT_DATE_CREATED))))
            .andExpect(jsonPath("$.[*].dateModified").value(hasItem(sameInstant(DEFAULT_DATE_MODIFIED))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())));
    }

    @Test
    public void getEquipment() throws Exception {
        // Initialize the database
        equipmentRepository.save(equipment);

        // Get the equipment
        restEquipmentMockMvc.perform(get("/api/equipment/{id}", equipment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(equipment.getId()))
            .andExpect(jsonPath("$.equipmentId").value(DEFAULT_EQUIPMENT_ID.toString()))
            .andExpect(jsonPath("$.equipmentName").value(DEFAULT_EQUIPMENT_NAME.toString()))
            .andExpect(jsonPath("$.dateLoadedOnSystem").value(sameInstant(DEFAULT_DATE_LOADED_ON_SYSTEM)))
            .andExpect(jsonPath("$.uploadedBy").value(DEFAULT_UPLOADED_BY.toString()))
            .andExpect(jsonPath("$.currentStatus").value(DEFAULT_CURRENT_STATUS.toString()))
            .andExpect(jsonPath("$.dateCreated").value(sameInstant(DEFAULT_DATE_CREATED)))
            .andExpect(jsonPath("$.dateModified").value(sameInstant(DEFAULT_DATE_MODIFIED)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()));
    }

    @Test
    public void getNonExistingEquipment() throws Exception {
        // Get the equipment
        restEquipmentMockMvc.perform(get("/api/equipment/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateEquipment() throws Exception {
        // Initialize the database
        equipmentRepository.save(equipment);
        int databaseSizeBeforeUpdate = equipmentRepository.findAll().size();

        // Update the equipment
        Equipment updatedEquipment = equipmentRepository.findOne(equipment.getId());
        updatedEquipment
            .equipmentId(UPDATED_EQUIPMENT_ID)
            .equipmentName(UPDATED_EQUIPMENT_NAME)
            .dateLoadedOnSystem(UPDATED_DATE_LOADED_ON_SYSTEM)
            .uploadedBy(UPDATED_UPLOADED_BY)
            .currentStatus(UPDATED_CURRENT_STATUS)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateModified(UPDATED_DATE_MODIFIED)
            .updatedBy(UPDATED_UPDATED_BY);
        EquipmentDTO equipmentDTO = equipmentMapper.toDto(updatedEquipment);

        restEquipmentMockMvc.perform(put("/api/equipment")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipmentDTO)))
            .andExpect(status().isOk());

        // Validate the Equipment in the database
        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeUpdate);
        Equipment testEquipment = equipmentList.get(equipmentList.size() - 1);
        assertThat(testEquipment.getEquipmentId()).isEqualTo(UPDATED_EQUIPMENT_ID);
        assertThat(testEquipment.getEquipmentName()).isEqualTo(UPDATED_EQUIPMENT_NAME);
        assertThat(testEquipment.getDateLoadedOnSystem()).isEqualTo(UPDATED_DATE_LOADED_ON_SYSTEM);
        assertThat(testEquipment.getUploadedBy()).isEqualTo(UPDATED_UPLOADED_BY);
        assertThat(testEquipment.getCurrentStatus()).isEqualTo(UPDATED_CURRENT_STATUS);
        assertThat(testEquipment.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testEquipment.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
        assertThat(testEquipment.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    public void updateNonExistingEquipment() throws Exception {
        int databaseSizeBeforeUpdate = equipmentRepository.findAll().size();

        // Create the Equipment
        EquipmentDTO equipmentDTO = equipmentMapper.toDto(equipment);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEquipmentMockMvc.perform(put("/api/equipment")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Equipment in the database
        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteEquipment() throws Exception {
        // Initialize the database
        equipmentRepository.save(equipment);
        int databaseSizeBeforeDelete = equipmentRepository.findAll().size();

        // Get the equipment
        restEquipmentMockMvc.perform(delete("/api/equipment/{id}", equipment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Equipment.class);
        Equipment equipment1 = new Equipment();
        equipment1.setId("id1");
        Equipment equipment2 = new Equipment();
        equipment2.setId(equipment1.getId());
        assertThat(equipment1).isEqualTo(equipment2);
        equipment2.setId("id2");
        assertThat(equipment1).isNotEqualTo(equipment2);
        equipment1.setId(null);
        assertThat(equipment1).isNotEqualTo(equipment2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EquipmentDTO.class);
        EquipmentDTO equipmentDTO1 = new EquipmentDTO();
        equipmentDTO1.setId("id1");
        EquipmentDTO equipmentDTO2 = new EquipmentDTO();
        assertThat(equipmentDTO1).isNotEqualTo(equipmentDTO2);
        equipmentDTO2.setId(equipmentDTO1.getId());
        assertThat(equipmentDTO1).isEqualTo(equipmentDTO2);
        equipmentDTO2.setId("id2");
        assertThat(equipmentDTO1).isNotEqualTo(equipmentDTO2);
        equipmentDTO1.setId(null);
        assertThat(equipmentDTO1).isNotEqualTo(equipmentDTO2);
    }
}
