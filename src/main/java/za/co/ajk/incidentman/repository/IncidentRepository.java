package za.co.ajk.incidentman.repository;

import za.co.ajk.incidentman.domain.Incident;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Incident entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IncidentRepository extends MongoRepository<Incident, String> {

}
