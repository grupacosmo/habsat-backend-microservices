package pl.edu.pk.cosmo.habsatbackend.authorizationserver.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class SessionResponse {
    private String sessionID;

}
