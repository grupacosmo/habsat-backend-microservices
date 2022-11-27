package pl.edu.pk.cosmo.habsatbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.pk.cosmo.habsatbackend.entity.Flight;
import pl.edu.pk.cosmo.habsatbackend.entity.assets.FlightStage;
import pl.edu.pk.cosmo.habsatbackend.entity.response.FlightResponse;

import java.util.Date;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {

    boolean existsByDate(Date date);
    Optional<Flight> findFlightByDate(Date date);

    @Query("SELECT f FROM Flight f WHERE f.flightStage=?1")
    Optional<Flight> findFlightByFlightStage(FlightStage flightStage);

    @Query("SELECT f FROM Flight f WHERE f.flightStage=2 ORDER BY f.date")
    Optional<Flight> findMostRecentFlight();
}
