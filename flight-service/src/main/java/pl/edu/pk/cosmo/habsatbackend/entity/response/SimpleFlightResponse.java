package pl.edu.pk.cosmo.habsatbackend.entity.response;

import lombok.Data;
import lombok.experimental.Accessors;
import pl.edu.pk.cosmo.habsatbackend.entity.assets.FlightStage;

import java.time.LocalDate;
import java.util.Date;

@Data
@Accessors(chain = true)
public class SimpleFlightResponse {
    private LocalDate date;
    private String description;
    private String title;
    private FlightStage flightStage;
}
