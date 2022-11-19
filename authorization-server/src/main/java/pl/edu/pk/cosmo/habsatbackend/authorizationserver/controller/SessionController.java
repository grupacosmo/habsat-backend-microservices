package pl.edu.pk.cosmo.habsatbackend.authorizationserver.controller;

import org.springframework.http.HttpStatus;
import pl.edu.pk.cosmo.habsatbackend.authorizationserver.entity.request.RequestSessionCreate;
import pl.edu.pk.cosmo.habsatbackend.authorizationserver.entity.response.SessionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.edu.pk.cosmo.habsatbackend.authorizationserver.exception.InvalidCredentialsException;
import pl.edu.pk.cosmo.habsatbackend.authorizationserver.service.SessionService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;

    @PostMapping("/create")
    public SessionResponse createSession(@RequestBody @Valid RequestSessionCreate requestSessionCreate) {
        try {
            sessionService.createSession(requestSessionCreate);
            return null;
        } catch (InvalidCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
