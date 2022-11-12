package pl.edu.pk.cosmo.habsatbackend.emailservice.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String email;
    private String name;
    private String surname;
    private String roles;
    private boolean firstLogin;
}
