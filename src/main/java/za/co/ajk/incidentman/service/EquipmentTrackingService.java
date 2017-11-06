package za.co.ajk.incidentman.service;

import za.co.ajk.incidentman.service.dto.EquipmentTrackingDTO;
import java.util.List;

/**
 * Service Interface for managing EquipmentTracking.
 */
public interface EquipmentTrackingService {

    /**
     * Save a equipmentTracking.
     *
     * @param equipmentTrackingDTO the entity to save
     * @return the persisted entity
     */
    EquipmentTrackingDTO save(EquipmentTrackingDTO equipmentTrackingDTO);

    /**
     *  Get all the equipmentTrackings.
     *
     *  @return the list of entities
     */
    List<EquipmentTrackingDTO> findAll();

    /**
     *  Get the "id" equipmentTracking.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EquipmentTrackingDTO findOne(String id);

    /**
     *  Delete the "id" equipmentTracking.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
