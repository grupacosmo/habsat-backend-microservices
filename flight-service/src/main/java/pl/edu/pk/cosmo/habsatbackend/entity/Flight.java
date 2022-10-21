package pl.edu.pk.cosmo.habsatbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="flight")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id")
    private Integer id;

    private Date date;
    private String description;
    private String title;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "flight_id")
    private List<FlightData> flightDataList;
}
