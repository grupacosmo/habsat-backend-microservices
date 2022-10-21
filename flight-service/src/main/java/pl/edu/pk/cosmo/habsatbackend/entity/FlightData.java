package pl.edu.pk.cosmo.habsatbackend.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="data_test")
@lombok.Data
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

    private int flight_id;
}
