package pl.edu.pk.cosmo.habsatbackend.userservice.converter;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class RoleConverter {
    public List<String> toList(String roles) {
        return List.of(roles.split(" "));
    }

    public String toString(List<String> roles) {
        return String.join(" ", roles).strip();
    }

    public String toString(Set<String> roles) {
        return String.join(" ", roles).strip();
    }
}
