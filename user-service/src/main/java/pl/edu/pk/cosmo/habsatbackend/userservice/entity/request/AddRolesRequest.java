package pl.edu.pk.cosmo.habsatbackend.userservice.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AddRolesRequest {
    private String email;
    private List<String> roles;
}
