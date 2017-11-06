package za.co.ajk.incidentman.service.mapper;

import za.co.ajk.incidentman.domain.*;
import za.co.ajk.incidentman.service.dto.IncidentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Incident and its DTO IncidentDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface IncidentMapper extends EntityMapper<IncidentDTO, Incident> {

    

    
}
