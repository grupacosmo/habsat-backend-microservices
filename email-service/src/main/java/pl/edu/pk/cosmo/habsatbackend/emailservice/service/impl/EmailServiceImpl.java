package pl.edu.pk.cosmo.habsatbackend.emailservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import pl.edu.pk.cosmo.habsatbackend.emailservice.entity.ExtendedMailMessage;
import pl.edu.pk.cosmo.habsatbackend.emailservice.entity.request.SendMailRequest;
import pl.edu.pk.cosmo.habsatbackend.emailservice.entity.request.SendManyMailsRequest;
import pl.edu.pk.cosmo.habsatbackend.emailservice.service.EmailService;
import pl.edu.pk.cosmo.habsatbackend.emailservice.service.UserService;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender emailSender;
    private final UserService userService;

    @Override
    public void sendSimpleMessage(SendMailRequest sendMailRequest) {
        ExtendedMailMessage message = new ExtendedMailMessage(sendMailRequest);
        emailSender.send(message);
    }

    @Override
    public void sendToMany(SendManyMailsRequest sendMailRequest) {
        ExtendedMailMessage message = new ExtendedMailMessage(sendMailRequest);
        emailSender.send(message);

        userService.getUsersByRole(sendMailRequest.getRequestedRole())
                .forEach(user -> {
                    emailSender.send(message.withSetTo(user.getEmail()));
                });
    }
}
