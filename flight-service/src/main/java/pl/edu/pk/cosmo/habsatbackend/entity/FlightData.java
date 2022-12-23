package pl.edu.pk.cosmo.habsatbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="data_test")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class FlightData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double speed;

    @Column(nullable = false)
    private Double altitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double temperature;

    @Column(nullable = false)
    private LocalDateTime time;

    @Column(nullable = false)
    private Double rssi;

    @Column(nullable = true)
    private String snr;

    @Column(nullable = true)
    private String channelIndex;

    @Column(nullable = true)
    private String consumedAirtime;

    @Column(nullable = true)
    private String spreadingFactor;

    @Column(nullable = true)
    private Integer flight_id;
}
