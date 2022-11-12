package pl.edu.pk.cosmo.habsatbackend.emailservice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.mail.SimpleMailMessage;
import pl.edu.pk.cosmo.habsatbackend.emailservice.entity.request.SendMailRequest;
import pl.edu.pk.cosmo.habsatbackend.emailservice.entity.request.SendManyMailsRequest;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ExtendedMailMessage extends SimpleMailMessage {
    public ExtendedMailMessage(SendMailRequest sendMailRequest) {
        setTo(sendMailRequest.getEmailTo());
        setFrom(sendMailRequest.getEmailFrom());
        setSubject(sendMailRequest.getSubject());
        setText(sendMailRequest.getText());
    }

    public ExtendedMailMessage(SendManyMailsRequest sendMailRequest) {
        setFrom(sendMailRequest.getPerson());
        setTo(sendMailRequest.getPerson());
        setSubject(sendMailRequest.getSubject());
        setText(sendMailRequest.getText());
    }

    public ExtendedMailMessage withSetTo(String... to) {
        super.setTo(to);
        return this;
    }
}
