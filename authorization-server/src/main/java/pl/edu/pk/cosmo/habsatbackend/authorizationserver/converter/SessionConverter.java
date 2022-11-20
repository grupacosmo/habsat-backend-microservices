package pl.edu.pk.cosmo.habsatbackend.authorizationserver.converter;

import org.springframework.stereotype.Component;
import pl.edu.pk.cosmo.habsatbackend.authorizationserver.entity.SessionEntity;
import pl.edu.pk.cosmo.habsatbackend.authorizationserver.entity.response.SessionResponse;
import pl.edu.pk.cosmo.habsatbackend.authorizationserver.entity.response.ValidatedSessionResponse;

@Component
public class SessionConverter {
    public SessionResponse sessionResponseOf(SessionEntity sessionEntity) {
        return new SessionResponse()
                .setSessionID(sessionEntity.getSessionId());
    }

    public ValidatedSessionResponse validatedSessionResponseOf(SessionEntity sessionEntity) {
        return new ValidatedSessionResponse()
                .setSessionId(sessionEntity.getSessionId())
                .setEmail(sessionEntity.getEmail())
                .setExpirationDate(sessionEntity.getExpirationDate());
    }
}
