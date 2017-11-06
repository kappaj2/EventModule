package za.co.ajk.incidentman.web.rest;

import com.codahale.metrics.annotation.Timed;
import za.co.ajk.incidentman.service.IncidentService;
import za.co.ajk.incidentman.web.rest.errors.BadRequestAlertException;
import za.co.ajk.incidentman.web.rest.util.HeaderUtil;
import za.co.ajk.incidentman.web.rest.util.PaginationUtil;
import za.co.ajk.incidentman.service.dto.IncidentDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Incident.
 */
@RestController
@RequestMapping("/api")
public class IncidentResource {

    private final Logger log = LoggerFactory.getLogger(IncidentResource.class);

    private static final String ENTITY_NAME = "incident";

    private final IncidentService incidentService;

    public IncidentResource(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    /**
     * POST  /incidents : Create a new incident.
     *
     * @param incidentDTO the incidentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new incidentDTO, or with status 400 (Bad Request) if the incident has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/incidents")
    @Timed
    public ResponseEntity<IncidentDTO> createIncident(@Valid @RequestBody IncidentDTO incidentDTO) throws URISyntaxException {
        log.debug("REST request to save Incident : {}", incidentDTO);
        if (incidentDTO.getId() != null) {
            throw new BadRequestAlertException("A new incident cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IncidentDTO result = incidentService.save(incidentDTO);
        return ResponseEntity.created(new URI("/api/incidents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /incidents : Updates an existing incident.
     *
     * @param incidentDTO the incidentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated incidentDTO,
     * or with status 400 (Bad Request) if the incidentDTO is not valid,
     * or with status 500 (Internal Server Error) if the incidentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/incidents")
    @Timed
    public ResponseEntity<IncidentDTO> updateIncident(@Valid @RequestBody IncidentDTO incidentDTO) throws URISyntaxException {
        log.debug("REST request to update Incident : {}", incidentDTO);
        if (incidentDTO.getId() == null) {
            return createIncident(incidentDTO);
        }
        IncidentDTO result = incidentService.save(incidentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, incidentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /incidents : get all the incidents.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of incidents in body
     */
    @GetMapping("/incidents")
    @Timed
    public ResponseEntity<List<IncidentDTO>> getAllIncidents(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Incidents");
        Page<IncidentDTO> page = incidentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/incidents");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /incidents/:id : get the "id" incident.
     *
     * @param id the id of the incidentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the incidentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/incidents/{id}")
    @Timed
    public ResponseEntity<IncidentDTO> getIncident(@PathVariable String id) {
        log.debug("REST request to get Incident : {}", id);
        IncidentDTO incidentDTO = incidentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(incidentDTO));
    }

    /**
     * DELETE  /incidents/:id : delete the "id" incident.
     *
     * @param id the id of the incidentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/incidents/{id}")
    @Timed
    public ResponseEntity<Void> deleteIncident(@PathVariable String id) {
        log.debug("REST request to delete Incident : {}", id);
        incidentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
