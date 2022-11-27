package pl.edu.pk.cosmo.habsatbackend.entity.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FlightDataResponse {
    private Double speed;
    private Double altitude;
    private Double longitude;
    private Double latitude;
    private Double temperature;
    private LocalDateTime time;
    private Double rssi;
}
