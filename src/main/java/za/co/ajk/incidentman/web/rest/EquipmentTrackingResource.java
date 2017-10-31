package za.co.ajk.incidentman.web.rest;

import com.codahale.metrics.annotation.Timed;
import za.co.ajk.incidentman.service.EquipmentTrackingService;
import za.co.ajk.incidentman.web.rest.errors.BadRequestAlertException;
import za.co.ajk.incidentman.web.rest.util.HeaderUtil;
import za.co.ajk.incidentman.service.dto.EquipmentTrackingDTO;
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
 * REST controller for managing EquipmentTracking.
 */
@RestController
@RequestMapping("/api")
public class EquipmentTrackingResource {

    private final Logger log = LoggerFactory.getLogger(EquipmentTrackingResource.class);

    private static final String ENTITY_NAME = "equipmentTracking";

    private final EquipmentTrackingService equipmentTrackingService;

    public EquipmentTrackingResource(EquipmentTrackingService equipmentTrackingService) {
        this.equipmentTrackingService = equipmentTrackingService;
    }

    /**
     * POST  /equipment-trackings : Create a new equipmentTracking.
     *
     * @param equipmentTrackingDTO the equipmentTrackingDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new equipmentTrackingDTO, or with status 400 (Bad Request) if the equipmentTracking has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/equipment-trackings")
    @Timed
    public ResponseEntity<EquipmentTrackingDTO> createEquipmentTracking(@Valid @RequestBody EquipmentTrackingDTO equipmentTrackingDTO) throws URISyntaxException {
        log.debug("REST request to save EquipmentTracking : {}", equipmentTrackingDTO);
        if (equipmentTrackingDTO.getId() != null) {
            throw new BadRequestAlertException("A new equipmentTracking cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EquipmentTrackingDTO result = equipmentTrackingService.save(equipmentTrackingDTO);
        return ResponseEntity.created(new URI("/api/equipment-trackings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /equipment-trackings : Updates an existing equipmentTracking.
     *
     * @param equipmentTrackingDTO the equipmentTrackingDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated equipmentTrackingDTO,
     * or with status 400 (Bad Request) if the equipmentTrackingDTO is not valid,
     * or with status 500 (Internal Server Error) if the equipmentTrackingDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/equipment-trackings")
    @Timed
    public ResponseEntity<EquipmentTrackingDTO> updateEquipmentTracking(@Valid @RequestBody EquipmentTrackingDTO equipmentTrackingDTO) throws URISyntaxException {
        log.debug("REST request to update EquipmentTracking : {}", equipmentTrackingDTO);
        if (equipmentTrackingDTO.getId() == null) {
            return createEquipmentTracking(equipmentTrackingDTO);
        }
        EquipmentTrackingDTO result = equipmentTrackingService.save(equipmentTrackingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, equipmentTrackingDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /equipment-trackings : get all the equipmentTrackings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of equipmentTrackings in body
     */
    @GetMapping("/equipment-trackings")
    @Timed
    public List<EquipmentTrackingDTO> getAllEquipmentTrackings() {
        log.debug("REST request to get all EquipmentTrackings");
        return equipmentTrackingService.findAll();
        }

    /**
     * GET  /equipment-trackings/:id : get the "id" equipmentTracking.
     *
     * @param id the id of the equipmentTrackingDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the equipmentTrackingDTO, or with status 404 (Not Found)
     */
    @GetMapping("/equipment-trackings/{id}")
    @Timed
    public ResponseEntity<EquipmentTrackingDTO> getEquipmentTracking(@PathVariable String id) {
        log.debug("REST request to get EquipmentTracking : {}", id);
        EquipmentTrackingDTO equipmentTrackingDTO = equipmentTrackingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(equipmentTrackingDTO));
    }

    /**
     * DELETE  /equipment-trackings/:id : delete the "id" equipmentTracking.
     *
     * @param id the id of the equipmentTrackingDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/equipment-trackings/{id}")
    @Timed
    public ResponseEntity<Void> deleteEquipmentTracking(@PathVariable String id) {
        log.debug("REST request to delete EquipmentTracking : {}", id);
        equipmentTrackingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
