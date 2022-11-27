package pl.edu.pk.cosmo.habsatbackend.entity.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pk.cosmo.habsatbackend.entity.assets.FlightStage;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewFlightRequest {

    @NotNull
    private Date date;

    @NotNull
    private String description;

    @NotNull
    private String title;
}
