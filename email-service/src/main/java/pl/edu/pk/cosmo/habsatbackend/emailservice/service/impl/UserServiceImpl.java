package pl.edu.pk.cosmo.habsatbackend.emailservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.edu.pk.cosmo.habsatbackend.emailservice.entity.User;
import pl.edu.pk.cosmo.habsatbackend.emailservice.service.UserService;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final RestTemplate restTemplate;

    @Override
    public List<User> getUsersByRole(String role) {
        return restTemplate.getForEntity(
                "http://user-service/user-service/user/" + role + "/users"
                , List.class
        ).getBody();
    }
}
