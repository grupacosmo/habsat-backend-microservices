package pl.edu.pk.cosmo.habsatbackend.gateway.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Data
@Slf4j
@NoArgsConstructor
@Accessors(chain = true)
public class ValidatedSessionResponse {
    private String sessionId;
    private LocalDateTime expirationDate;
    private String email;
}
