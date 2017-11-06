package za.co.ajk.incidentman.service.mapper;

import za.co.ajk.incidentman.domain.*;
import za.co.ajk.incidentman.service.dto.IncidentProgressDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity IncidentProgress and its DTO IncidentProgressDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface IncidentProgressMapper extends EntityMapper<IncidentProgressDTO, IncidentProgress> {

    

    
}
