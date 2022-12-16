package pl.edu.pk.cosmo.habsatbackend.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edu.pk.cosmo.habsatbackend.entity.Flight;
import pl.edu.pk.cosmo.habsatbackend.entity.assets.FlightStage;
import pl.edu.pk.cosmo.habsatbackend.entity.request.NewFlightRequest;
import pl.edu.pk.cosmo.habsatbackend.entity.response.FlightResponse;
import pl.edu.pk.cosmo.habsatbackend.entity.response.SimpleFlightResponse;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FlightConverter {
    private final DataConverter dataConverter;

    public FlightResponse responeOf(Flight flight) {
        return new FlightResponse()
                .setDate(flight.getDate())
                .setDescription(flight.getDescription())
                .setTitle(flight.getTitle())
                .setFlightDataResponseList(
                        flight.getFlightDataList()
                                .stream()
                                .map(dataConverter::responseOf)
                                .collect(Collectors.toList())
                );

    }

    public SimpleFlightResponse simpleResponseOf(Flight flight) {
        return new SimpleFlightResponse()
                .setDescription(flight.getDescription())
                .setTitle(flight.getTitle())
                .setDate(flight.getDate())
                .setFlightStage(flight.getFlightStage());
    }

    public Flight toFlight(NewFlightRequest newFlightRequest) {
        return new Flight()
                .setDescription(newFlightRequest.getDescription())
                .setTitle(newFlightRequest.getTitle())
                .setDate(newFlightRequest.getDate())
                .setFlightStage(FlightStage.ONGOING);
    }
}
