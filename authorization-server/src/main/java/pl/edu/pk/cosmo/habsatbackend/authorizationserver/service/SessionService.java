package pl.edu.pk.cosmo.habsatbackend.authorizationserver.service;

import pl.edu.pk.cosmo.habsatbackend.authorizationserver.entity.request.RequestSessionCreate;
import pl.edu.pk.cosmo.habsatbackend.authorizationserver.entity.response.SessionResponse;
import pl.edu.pk.cosmo.habsatbackend.authorizationserver.exception.InvalidCredentialsException;

public interface SessionService {
    SessionResponse createSession(RequestSessionCreate requestSessionCreate) throws InvalidCredentialsException;
}
