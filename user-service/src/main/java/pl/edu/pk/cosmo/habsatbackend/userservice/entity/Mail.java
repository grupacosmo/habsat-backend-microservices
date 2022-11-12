package pl.edu.pk.cosmo.habsatbackend.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Mail {
    private String emailFrom = "noreply@cosmopk.edu.pl";
    private String emailTo;
    private String subject;
    private String text;
}
