package pl.edu.pk.cosmo.habsatbackend.authorizationserver.service.impl;

import pl.edu.pk.cosmo.habsatbackend.authorizationserver.entity.response.ValidatedSessionResponse;
import pl.edu.pk.cosmo.habsatbackend.authorizationserver.exception.InvalidSessionID;
import pl.edu.pk.cosmo.habsatbackend.authorizationserver.repository.SessionRepository;
import pl.edu.pk.cosmo.habsatbackend.authorizationserver.converter.SessionConverter;
import pl.edu.pk.cosmo.habsatbackend.authorizationserver.entity.SessionEntity;
import pl.edu.pk.cosmo.habsatbackend.authorizationserver.entity.request.RequestSessionCreate;
import pl.edu.pk.cosmo.habsatbackend.authorizationserver.entity.response.SessionResponse;
import pl.edu.pk.cosmo.habsatbackend.authorizationserver.entity.response.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.edu.pk.cosmo.habsatbackend.authorizationserver.exception.InvalidCredentialsException;
import pl.edu.pk.cosmo.habsatbackend.authorizationserver.service.SessionService;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final UserService userService;
    private final SessionRepository sessionRepository;
    private final SessionConverter sessionConverter;

    @Override
    public SessionResponse createSession(RequestSessionCreate requestSessionCreate) throws InvalidCredentialsException {
        final HttpStatus status = userService.checkUserCredentials(requestSessionCreate);
        if(status.is4xxClientError())
            throw new InvalidCredentialsException("Invalid credentials!");

        if(sessionRepository.existsByEmail(requestSessionCreate.getEmail()))
            return sessionConverter.sessionResponseOf(sessionRepository.findByEmail(requestSessionCreate.getEmail()));

        final SessionEntity sessionEntity = new SessionEntity()
                .setSessionId(generateSessionId())
                .setEmail(requestSessionCreate.getEmail())
                .setExpirationDate(LocalDateTime.now().plusDays(1));

        sessionRepository.save(sessionEntity);
        return sessionConverter.sessionResponseOf(sessionEntity);
    }

    @Override
    public ValidatedSessionResponse validateSession(String sessionID) throws InvalidSessionID {
        final SessionEntity sessionEntity = sessionRepository.findById(sessionID)
                .orElseThrow(()-> new InvalidSessionID("There is no active session with given id"));

        // TODO add date and mac / IP validation

        return sessionConverter.validatedSessionResponseOf(sessionEntity);
    }

    private String generateSessionId() {
        String randomSessionID;
        do {
            randomSessionID = UUID.randomUUID().toString().replaceAll("-", "");
        } while(sessionRepository.existsById(randomSessionID));

        return randomSessionID;
    }
}
