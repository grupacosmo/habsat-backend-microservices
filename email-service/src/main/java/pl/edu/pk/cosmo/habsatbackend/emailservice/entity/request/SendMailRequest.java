package pl.edu.pk.cosmo.habsatbackend.emailservice.entity.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class SendMailRequest {
    private String emailFrom;
    private String emailTo;
    private String subject;
    private String text;
}
