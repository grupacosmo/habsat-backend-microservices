package pl.edu.pk.cosmo.habsatbackend.authorizationserver.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestSessionCreate {
    private String email;
    private String password;
}
