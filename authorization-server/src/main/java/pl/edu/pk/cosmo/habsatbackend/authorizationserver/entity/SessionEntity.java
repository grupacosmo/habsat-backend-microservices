package pl.edu.pk.cosmo.habsatbackend.authorizationserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("session")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class SessionEntity {
    @Id
    private String sessionId;
    private LocalDateTime expirationDate;
    private String email;
}
