package pl.edu.pk.cosmo.habsatbackend.emailservice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Email {
    private String emailFrom;
    private String content;
}
