package pl.edu.pk.cosmo.habsatbackend.repository;

import org.apache.tomcat.jni.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.pk.cosmo.habsatbackend.entity.Flight;
import pl.edu.pk.cosmo.habsatbackend.entity.assets.FlightStage;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {

    boolean existsByDate(LocalDate date);
    Optional<Flight> findFlightByDate(LocalDate date);

    @Query("SELECT f FROM Flight f WHERE f.flightStage=?1")
    List<Flight> findFlightByFlightStage(FlightStage flightStage);

    @Query("SELECT f FROM Flight f WHERE f.flightStage=0")
    Optional<Flight> findOngoing();
}
