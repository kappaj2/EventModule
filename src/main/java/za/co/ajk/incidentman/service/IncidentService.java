package za.co.ajk.incidentman.service;

import za.co.ajk.incidentman.service.dto.IncidentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Incident.
 */
public interface IncidentService {

    /**
     * Save a incident.
     *
     * @param incidentDTO the entity to save
     * @return the persisted entity
     */
    IncidentDTO save(IncidentDTO incidentDTO);

    /**
     *  Get all the incidents.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<IncidentDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" incident.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    IncidentDTO findOne(String id);

    /**
     *  Delete the "id" incident.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
