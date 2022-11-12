package pl.edu.pk.cosmo.habsatbackend.userservice.service;

import pl.edu.pk.cosmo.habsatbackend.userservice.entity.Mail;

public interface EmailService {
    void send(Mail mail);
}
