package pl.edu.pk.cosmo.habsatbackend.userservice.entity.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserResponse {
    private String email;
    private String name;
    private String surname;
    private String roles;
    private boolean firstLogin;
}
