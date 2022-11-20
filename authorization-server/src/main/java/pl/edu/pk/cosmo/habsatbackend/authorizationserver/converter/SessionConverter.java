package pl.edu.pk.cosmo.habsatbackend.authorizationserver.converter;

import org.springframework.stereotype.Component;
import pl.edu.pk.cosmo.habsatbackend.authorizationserver.entity.SessionEntity;
import pl.edu.pk.cosmo.habsatbackend.authorizationserver.entity.response.SessionResponse;

@Component
public class SessionConverter {
    public SessionResponse sessionResponseOf(SessionEntity sessionEntity) {
        return new SessionResponse()
                .setSessionID(sessionEntity.getSessionId());
    }
}
