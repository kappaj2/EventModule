package za.co.ajk.incidentman.service.mapper;

import za.co.ajk.incidentman.domain.*;
import za.co.ajk.incidentman.service.dto.EquipmentTrackingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EquipmentTracking and its DTO EquipmentTrackingDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EquipmentTrackingMapper extends EntityMapper<EquipmentTrackingDTO, EquipmentTracking> {

    

    
}
