package pl.edu.pk.cosmo.habsatbackend.entity.tmp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class User {
    private String password;
    private String email;
}
