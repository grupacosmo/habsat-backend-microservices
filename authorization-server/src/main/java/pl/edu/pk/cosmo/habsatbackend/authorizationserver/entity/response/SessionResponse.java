package pl.edu.pk.cosmo.habsatbackend.authorizationserver.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SessionResponse {
    private String sessionID;

}
