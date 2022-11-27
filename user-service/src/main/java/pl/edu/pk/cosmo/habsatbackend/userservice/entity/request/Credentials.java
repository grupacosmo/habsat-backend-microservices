package pl.edu.pk.cosmo.habsatbackend.userservice.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Credentials {
    private String email;
    private String password;
}
