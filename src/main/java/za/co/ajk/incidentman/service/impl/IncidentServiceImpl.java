package za.co.ajk.incidentman.service.impl;

import za.co.ajk.incidentman.service.IncidentService;
import za.co.ajk.incidentman.domain.Incident;
import za.co.ajk.incidentman.repository.IncidentRepository;
import za.co.ajk.incidentman.service.dto.IncidentDTO;
import za.co.ajk.incidentman.service.mapper.IncidentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing Incident.
 */
@Service
public class IncidentServiceImpl implements IncidentService{

    private final Logger log = LoggerFactory.getLogger(IncidentServiceImpl.class);

    private final IncidentRepository incidentRepository;

    private final IncidentMapper incidentMapper;

    public IncidentServiceImpl(IncidentRepository incidentRepository, IncidentMapper incidentMapper) {
        this.incidentRepository = incidentRepository;
        this.incidentMapper = incidentMapper;
    }

    /**
     * Save a incident.
     *
     * @param incidentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public IncidentDTO save(IncidentDTO incidentDTO) {
        log.debug("Request to save Incident : {}", incidentDTO);
        Incident incident = incidentMapper.toEntity(incidentDTO);
        incident = incidentRepository.save(incident);
        return incidentMapper.toDto(incident);
    }

    /**
     *  Get all the incidents.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<IncidentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Incidents");
        return incidentRepository.findAll(pageable)
            .map(incidentMapper::toDto);
    }

    /**
     *  Get one incident by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public IncidentDTO findOne(String id) {
        log.debug("Request to get Incident : {}", id);
        Incident incident = incidentRepository.findOne(id);
        return incidentMapper.toDto(incident);
    }

    /**
     *  Delete the  incident by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Incident : {}", id);
        incidentRepository.delete(id);
    }
}
