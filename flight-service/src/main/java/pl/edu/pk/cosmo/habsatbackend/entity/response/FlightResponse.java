package pl.edu.pk.cosmo.habsatbackend.entity.response;

import lombok.Data;
import lombok.experimental.Accessors;
import pl.edu.pk.cosmo.habsatbackend.entity.assets.FlightStage;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class FlightResponse {
    private LocalDateTime date;
    private String description;
    private String title;
    private FlightStage flightStage;
    private List<FlightDataResponse> flightDataResponseList;
}
