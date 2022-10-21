package pl.edu.pk.cosmo.habsatbackend.entity.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class FlightResponse {
    private Integer id;
    private Date date;
    private String description;
}
