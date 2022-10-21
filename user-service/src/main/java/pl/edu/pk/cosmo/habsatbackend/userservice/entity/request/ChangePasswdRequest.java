package pl.edu.pk.cosmo.habsatbackend.userservice.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswdRequest {
    private String email;
    private String oldPassword;
    private String newPassword;
}
