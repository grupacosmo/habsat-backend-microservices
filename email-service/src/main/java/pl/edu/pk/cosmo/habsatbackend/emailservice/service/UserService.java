package pl.edu.pk.cosmo.habsatbackend.emailservice.service;

import pl.edu.pk.cosmo.habsatbackend.emailservice.entity.User;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

public interface UserService {
    List<User> getUsersByRole(String role);
}
