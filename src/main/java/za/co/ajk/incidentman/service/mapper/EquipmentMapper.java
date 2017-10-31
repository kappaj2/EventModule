package za.co.ajk.incidentman.service.mapper;

import za.co.ajk.incidentman.domain.*;
import za.co.ajk.incidentman.service.dto.EquipmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Equipment and its DTO EquipmentDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EquipmentMapper extends EntityMapper<EquipmentDTO, Equipment> {

    

    
}
