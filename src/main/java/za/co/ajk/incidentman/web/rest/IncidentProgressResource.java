package za.co.ajk.incidentman.web.rest;

import com.codahale.metrics.annotation.Timed;
import za.co.ajk.incidentman.service.IncidentProgressService;
import za.co.ajk.incidentman.web.rest.errors.BadRequestAlertException;
import za.co.ajk.incidentman.web.rest.util.HeaderUtil;
import za.co.ajk.incidentman.service.dto.IncidentProgressDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing IncidentProgress.
 */
@RestController
@RequestMapping("/api")
public class IncidentProgressResource {

    private final Logger log = LoggerFactory.getLogger(IncidentProgressResource.class);

    private static final String ENTITY_NAME = "incidentProgress";

    private final IncidentProgressService incidentProgressService;

    public IncidentProgressResource(IncidentProgressService incidentProgressService) {
        this.incidentProgressService = incidentProgressService;
    }

    /**
     * POST  /incident-progresses : Create a new incidentProgress.
     *
     * @param incidentProgressDTO the incidentProgressDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new incidentProgressDTO, or with status 400 (Bad Request) if the incidentProgress has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/incident-progresses")
    @Timed
    public ResponseEntity<IncidentProgressDTO> createIncidentProgress(@Valid @RequestBody IncidentProgressDTO incidentProgressDTO) throws URISyntaxException {
        log.debug("REST request to save IncidentProgress : {}", incidentProgressDTO);
        if (incidentProgressDTO.getId() != null) {
            throw new BadRequestAlertException("A new incidentProgress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IncidentProgressDTO result = incidentProgressService.save(incidentProgressDTO);
        return ResponseEntity.created(new URI("/api/incident-progresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /incident-progresses : Updates an existing incidentProgress.
     *
     * @param incidentProgressDTO the incidentProgressDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated incidentProgressDTO,
     * or with status 400 (Bad Request) if the incidentProgressDTO is not valid,
     * or with status 500 (Internal Server Error) if the incidentProgressDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/incident-progresses")
    @Timed
    public ResponseEntity<IncidentProgressDTO> updateIncidentProgress(@Valid @RequestBody IncidentProgressDTO incidentProgressDTO) throws URISyntaxException {
        log.debug("REST request to update IncidentProgress : {}", incidentProgressDTO);
        if (incidentProgressDTO.getId() == null) {
            return createIncidentProgress(incidentProgressDTO);
        }
        IncidentProgressDTO result = incidentProgressService.save(incidentProgressDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, incidentProgressDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /incident-progresses : get all the incidentProgresses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of incidentProgresses in body
     */
    @GetMapping("/incident-progresses")
    @Timed
    public List<IncidentProgressDTO> getAllIncidentProgresses() {
        log.debug("REST request to get all IncidentProgresses");
        return incidentProgressService.findAll();
        }

    /**
     * GET  /incident-progresses/:id : get the "id" incidentProgress.
     *
     * @param id the id of the incidentProgressDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the incidentProgressDTO, or with status 404 (Not Found)
     */
    @GetMapping("/incident-progresses/{id}")
    @Timed
    public ResponseEntity<IncidentProgressDTO> getIncidentProgress(@PathVariable String id) {
        log.debug("REST request to get IncidentProgress : {}", id);
        IncidentProgressDTO incidentProgressDTO = incidentProgressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(incidentProgressDTO));
    }

    /**
     * DELETE  /incident-progresses/:id : delete the "id" incidentProgress.
     *
     * @param id the id of the incidentProgressDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/incident-progresses/{id}")
    @Timed
    public ResponseEntity<Void> deleteIncidentProgress(@PathVariable String id) {
        log.debug("REST request to delete IncidentProgress : {}", id);
        incidentProgressService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
