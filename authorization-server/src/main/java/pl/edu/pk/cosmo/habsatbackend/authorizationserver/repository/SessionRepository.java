package pl.edu.pk.cosmo.habsatbackend.authorizationserver.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pk.cosmo.habsatbackend.authorizationserver.entity.SessionEntity;

@Repository
public interface SessionRepository extends MongoRepository<SessionEntity, String> {
    boolean existsByEmail(String email);
    SessionEntity findByEmail(String email);
}
