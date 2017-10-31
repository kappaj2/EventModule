package za.co.ajk.incidentman.repository;

import za.co.ajk.incidentman.domain.IncidentProgress;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the IncidentProgress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IncidentProgressRepository extends MongoRepository<IncidentProgress, String> {

}
