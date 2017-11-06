package za.co.ajk.incidentman.service;

import za.co.ajk.incidentman.service.dto.IncidentProgressDTO;
import java.util.List;

/**
 * Service Interface for managing IncidentProgress.
 */
public interface IncidentProgressService {

    /**
     * Save a incidentProgress.
     *
     * @param incidentProgressDTO the entity to save
     * @return the persisted entity
     */
    IncidentProgressDTO save(IncidentProgressDTO incidentProgressDTO);

    /**
     *  Get all the incidentProgresses.
     *
     *  @return the list of entities
     */
    List<IncidentProgressDTO> findAll();

    /**
     *  Get the "id" incidentProgress.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    IncidentProgressDTO findOne(String id);

    /**
     *  Delete the "id" incidentProgress.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
