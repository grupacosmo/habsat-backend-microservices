package pl.edu.pk.cosmo.habsatbackend.authorizationserver.entity.response;

import pl.edu.pk.cosmo.habsatbackend.authorizationserver.entity.request.RequestSessionCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserService {
    private final RestTemplate restTemplate;

    public HttpStatus checkUserCredentials(RequestSessionCreate requestSessionCreate) {
        return restTemplate.postForEntity(
                "http://localhost:8050/user-service/user/validate",
                requestSessionCreate,
                Object.class).getStatusCode();

    }
}
