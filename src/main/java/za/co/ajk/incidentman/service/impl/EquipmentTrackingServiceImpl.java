package za.co.ajk.incidentman.service.impl;

import za.co.ajk.incidentman.service.EquipmentTrackingService;
import za.co.ajk.incidentman.domain.EquipmentTracking;
import za.co.ajk.incidentman.repository.EquipmentTrackingRepository;
import za.co.ajk.incidentman.service.dto.EquipmentTrackingDTO;
import za.co.ajk.incidentman.service.mapper.EquipmentTrackingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing EquipmentTracking.
 */
@Service
public class EquipmentTrackingServiceImpl implements EquipmentTrackingService{

    private final Logger log = LoggerFactory.getLogger(EquipmentTrackingServiceImpl.class);

    private final EquipmentTrackingRepository equipmentTrackingRepository;

    private final EquipmentTrackingMapper equipmentTrackingMapper;

    public EquipmentTrackingServiceImpl(EquipmentTrackingRepository equipmentTrackingRepository, EquipmentTrackingMapper equipmentTrackingMapper) {
        this.equipmentTrackingRepository = equipmentTrackingRepository;
        this.equipmentTrackingMapper = equipmentTrackingMapper;
    }

    /**
     * Save a equipmentTracking.
     *
     * @param equipmentTrackingDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EquipmentTrackingDTO save(EquipmentTrackingDTO equipmentTrackingDTO) {
        log.debug("Request to save EquipmentTracking : {}", equipmentTrackingDTO);
        EquipmentTracking equipmentTracking = equipmentTrackingMapper.toEntity(equipmentTrackingDTO);
        equipmentTracking = equipmentTrackingRepository.save(equipmentTracking);
        return equipmentTrackingMapper.toDto(equipmentTracking);
    }

    /**
     *  Get all the equipmentTrackings.
     *
     *  @return the list of entities
     */
    @Override
    public List<EquipmentTrackingDTO> findAll() {
        log.debug("Request to get all EquipmentTrackings");
        return equipmentTrackingRepository.findAll().stream()
            .map(equipmentTrackingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one equipmentTracking by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public EquipmentTrackingDTO findOne(String id) {
        log.debug("Request to get EquipmentTracking : {}", id);
        EquipmentTracking equipmentTracking = equipmentTrackingRepository.findOne(id);
        return equipmentTrackingMapper.toDto(equipmentTracking);
    }

    /**
     *  Delete the  equipmentTracking by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete EquipmentTracking : {}", id);
        equipmentTrackingRepository.delete(id);
    }
}
