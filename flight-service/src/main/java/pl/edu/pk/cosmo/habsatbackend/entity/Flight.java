package pl.edu.pk.cosmo.habsatbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import pl.edu.pk.cosmo.habsatbackend.entity.assets.FlightStage;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "flight")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id")
    private Integer flightId;

    private LocalDate date;

    private String description;

    private String title;

    @Enumerated
    private FlightStage flightStage;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "flight_id")
    private List<FlightData> flightDataList;
}
