package pl.edu.pk.cosmo.habsatbackend.emailservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pk.cosmo.habsatbackend.emailservice.entity.Email;
import pl.edu.pk.cosmo.habsatbackend.emailservice.entity.request.SendMailRequest;
import pl.edu.pk.cosmo.habsatbackend.emailservice.entity.request.SendManyMailsRequest;
import pl.edu.pk.cosmo.habsatbackend.emailservice.service.EmailService;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/email-service")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.OK)
    public void sendMail(@RequestBody SendMailRequest email) throws MalformedURLException, URISyntaxException {
        emailService.sendSimpleMessage(email);
    }

    @PostMapping("/sendMany")
    @ResponseStatus(HttpStatus.OK)
    public void sendToMany(@RequestBody SendManyMailsRequest email) throws MalformedURLException, URISyntaxException {
        emailService.sendToMany(email);
    }
}
