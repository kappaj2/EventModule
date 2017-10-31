package za.co.ajk.incidentman.service.impl;

import za.co.ajk.incidentman.service.EquipmentService;
import za.co.ajk.incidentman.domain.Equipment;
import za.co.ajk.incidentman.repository.EquipmentRepository;
import za.co.ajk.incidentman.service.dto.EquipmentDTO;
import za.co.ajk.incidentman.service.mapper.EquipmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing Equipment.
 */
@Service
public class EquipmentServiceImpl implements EquipmentService{

    private final Logger log = LoggerFactory.getLogger(EquipmentServiceImpl.class);

    private final EquipmentRepository equipmentRepository;

    private final EquipmentMapper equipmentMapper;

    public EquipmentServiceImpl(EquipmentRepository equipmentRepository, EquipmentMapper equipmentMapper) {
        this.equipmentRepository = equipmentRepository;
        this.equipmentMapper = equipmentMapper;
    }

    /**
     * Save a equipment.
     *
     * @param equipmentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EquipmentDTO save(EquipmentDTO equipmentDTO) {
        log.debug("Request to save Equipment : {}", equipmentDTO);
        Equipment equipment = equipmentMapper.toEntity(equipmentDTO);
        equipment = equipmentRepository.save(equipment);
        return equipmentMapper.toDto(equipment);
    }

    /**
     *  Get all the equipment.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<EquipmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Equipment");
        return equipmentRepository.findAll(pageable)
            .map(equipmentMapper::toDto);
    }

    /**
     *  Get one equipment by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public EquipmentDTO findOne(String id) {
        log.debug("Request to get Equipment : {}", id);
        Equipment equipment = equipmentRepository.findOne(id);
        return equipmentMapper.toDto(equipment);
    }

    /**
     *  Delete the  equipment by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Equipment : {}", id);
        equipmentRepository.delete(id);
    }
}
