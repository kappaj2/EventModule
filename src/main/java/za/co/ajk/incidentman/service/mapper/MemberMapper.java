package za.co.ajk.incidentman.service.mapper;

import za.co.ajk.incidentman.domain.*;
import za.co.ajk.incidentman.service.dto.MemberDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Member and its DTO MemberDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MemberMapper extends EntityMapper<MemberDTO, Member> {

    

    
}
