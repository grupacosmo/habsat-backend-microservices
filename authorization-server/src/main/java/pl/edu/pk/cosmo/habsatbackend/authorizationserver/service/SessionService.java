package pl.edu.pk.cosmo.habsatbackend.authorizationserver.service;

import pl.edu.pk.cosmo.habsatbackend.authorizationserver.entity.request.RequestSessionCreate;
import pl.edu.pk.cosmo.habsatbackend.authorizationserver.entity.response.SessionResponse;
import pl.edu.pk.cosmo.habsatbackend.authorizationserver.entity.response.ValidatedSessionResponse;
import pl.edu.pk.cosmo.habsatbackend.authorizationserver.exception.InvalidCredentialsException;
import pl.edu.pk.cosmo.habsatbackend.authorizationserver.exception.InvalidSessionID;

public interface SessionService {
    SessionResponse createSession(RequestSessionCreate requestSessionCreate) throws InvalidCredentialsException;
    ValidatedSessionResponse validateSession(String sessionID) throws InvalidSessionID;
}
