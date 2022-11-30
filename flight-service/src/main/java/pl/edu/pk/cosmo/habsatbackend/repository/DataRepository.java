package pl.edu.pk.cosmo.habsatbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.pk.cosmo.habsatbackend.entity.FlightData;

@Repository
public interface DataRepository extends JpaRepository<FlightData, Long> {
    @Modifying
    @Query("DELETE FROM FlightData f WHERE f.flight_id = ?1 ")
    void deleteAllByFlight_id(Integer flightId);
}
