package za.co.ajk.incidentman.repository;

import za.co.ajk.incidentman.domain.Member;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Member entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MemberRepository extends MongoRepository<Member, String> {

}
