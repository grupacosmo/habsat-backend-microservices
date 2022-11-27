package pl.edu.pk.cosmo.habsatbackend.entity.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
public class FlightResponse {
    private Date date;
    private String description;
    private String title;
    private List<FlightDataResponse> flightDataResponseList;
}
