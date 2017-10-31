package za.co.ajk.incidentman.repository;

import za.co.ajk.incidentman.domain.EquipmentTracking;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the EquipmentTracking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EquipmentTrackingRepository extends MongoRepository<EquipmentTracking, String> {

}
