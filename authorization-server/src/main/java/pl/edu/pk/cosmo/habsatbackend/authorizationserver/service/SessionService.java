package pl.edu.pk.cosmo.habsatbackend.authorizationserver.service;

import pl.edu.pk.cosmo.habsatbackend.authorizationserver.entity.SessionEntity;
import pl.edu.pk.cosmo.habsatbackend.authorizationserver.entity.request.RequestSessionCreate;
import pl.edu.pk.cosmo.habsatbackend.authorizationserver.entity.response.SessionResponse;
import pl.edu.pk.cosmo.habsatbackend.authorizationserver.entity.response.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.edu.pk.cosmo.habsatbackend.authorizationserver.exception.InvalidCredentialsException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final UserService userService;

    public SessionResponse createSession(RequestSessionCreate requestSessionCreate) throws InvalidCredentialsException {
        HttpStatus status = userService.checkUserCredentials(requestSessionCreate);
        if(status.is4xxClientError())
            throw new InvalidCredentialsException("Invalid credentials!");

        final SessionEntity sessionEntity = new SessionEntity()
                .setSessionId(generateSessionId())
                .setEmail(requestSessionCreate.getEmail())
                .setExpirationDate(LocalDateTime.now().plusDays(1));

        return null;
    }

    private String generateSessionId() {
        return "RANDOM";
    }
}
