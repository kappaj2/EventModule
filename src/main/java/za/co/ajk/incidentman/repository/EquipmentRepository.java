package za.co.ajk.incidentman.repository;

import za.co.ajk.incidentman.domain.Equipment;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Equipment entity.
 */
@SuppressWarnings("unused")
@Repository
@JaversSpringDataAuditable
public interface EquipmentRepository extends MongoRepository<Equipment, String> {

}
