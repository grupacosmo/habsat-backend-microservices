package pl.edu.pk.cosmo.habsatbackend.converter;

import org.springframework.stereotype.Component;
import pl.edu.pk.cosmo.habsatbackend.entity.Flight;
import pl.edu.pk.cosmo.habsatbackend.entity.response.FlightResponse;

@Component
public class FlightConverter {

    public FlightResponse convertFlightToFlightResponse(Flight flight) {
        return new FlightResponse()
                .setId(flight.getId())
                .setDate(flight.getDate())
                .setDescription(flight.getDescription());
    }
}
