package pl.edu.pk.cosmo.habsatbackend.emailservice.service;

import pl.edu.pk.cosmo.habsatbackend.emailservice.entity.request.SendMailRequest;
import pl.edu.pk.cosmo.habsatbackend.emailservice.entity.request.SendMailsByRoleRequest;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public interface EmailService {
    void sendSimpleMessage(SendMailRequest sendMailRequest) throws MalformedURLException, URISyntaxException;
    void sendToMany(SendMailsByRoleRequest sendMailRequest);
}
