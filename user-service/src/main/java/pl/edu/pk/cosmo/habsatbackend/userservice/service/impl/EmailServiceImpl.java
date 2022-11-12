package pl.edu.pk.cosmo.habsatbackend.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.edu.pk.cosmo.habsatbackend.userservice.entity.Mail;
import pl.edu.pk.cosmo.habsatbackend.userservice.service.EmailService;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final RestTemplate restTemplate;

    @Override
    public void send(Mail mail) {
        // TODO add check for response code :)
        ResponseEntity<?> response = restTemplate.postForEntity("http://email-service/email-service/send", mail, Object.class);
    }
}
