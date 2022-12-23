package pl.edu.pk.cosmo.habsatbackend.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewFlightRequest {

    @NotNull
    private LocalDateTime date;

    @NotNull
    private String description;

    @NotNull
    private String title;
}
