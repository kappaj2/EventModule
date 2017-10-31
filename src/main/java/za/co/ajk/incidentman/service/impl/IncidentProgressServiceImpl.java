package za.co.ajk.incidentman.service.impl;

import za.co.ajk.incidentman.service.IncidentProgressService;
import za.co.ajk.incidentman.domain.IncidentProgress;
import za.co.ajk.incidentman.repository.IncidentProgressRepository;
import za.co.ajk.incidentman.service.dto.IncidentProgressDTO;
import za.co.ajk.incidentman.service.mapper.IncidentProgressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing IncidentProgress.
 */
@Service
public class IncidentProgressServiceImpl implements IncidentProgressService{

    private final Logger log = LoggerFactory.getLogger(IncidentProgressServiceImpl.class);

    private final IncidentProgressRepository incidentProgressRepository;

    private final IncidentProgressMapper incidentProgressMapper;

    public IncidentProgressServiceImpl(IncidentProgressRepository incidentProgressRepository, IncidentProgressMapper incidentProgressMapper) {
        this.incidentProgressRepository = incidentProgressRepository;
        this.incidentProgressMapper = incidentProgressMapper;
    }

    /**
     * Save a incidentProgress.
     *
     * @param incidentProgressDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public IncidentProgressDTO save(IncidentProgressDTO incidentProgressDTO) {
        log.debug("Request to save IncidentProgress : {}", incidentProgressDTO);
        IncidentProgress incidentProgress = incidentProgressMapper.toEntity(incidentProgressDTO);
        incidentProgress = incidentProgressRepository.save(incidentProgress);
        return incidentProgressMapper.toDto(incidentProgress);
    }

    /**
     *  Get all the incidentProgresses.
     *
     *  @return the list of entities
     */
    @Override
    public List<IncidentProgressDTO> findAll() {
        log.debug("Request to get all IncidentProgresses");
        return incidentProgressRepository.findAll().stream()
            .map(incidentProgressMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one incidentProgress by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public IncidentProgressDTO findOne(String id) {
        log.debug("Request to get IncidentProgress : {}", id);
        IncidentProgress incidentProgress = incidentProgressRepository.findOne(id);
        return incidentProgressMapper.toDto(incidentProgress);
    }

    /**
     *  Delete the  incidentProgress by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete IncidentProgress : {}", id);
        incidentProgressRepository.delete(id);
    }
}
