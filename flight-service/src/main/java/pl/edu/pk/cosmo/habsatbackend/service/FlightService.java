package pl.edu.pk.cosmo.habsatbackend.service;

import pl.edu.pk.cosmo.habsatbackend.entity.assets.FlightStage;
import pl.edu.pk.cosmo.habsatbackend.entity.request.NewFlightRequest;
import pl.edu.pk.cosmo.habsatbackend.entity.response.FlightResponse;
import pl.edu.pk.cosmo.habsatbackend.entity.response.SimpleFlightResponse;
import pl.edu.pk.cosmo.habsatbackend.exception.FlightAlreadyExistsException;
import pl.edu.pk.cosmo.habsatbackend.exception.NoFlightException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public interface FlightService {
    FlightResponse getFlightByDate(LocalDate date) throws NoFlightException;
    void createNewFlight(NewFlightRequest newFlightRequest) throws FlightAlreadyExistsException;
    List<FlightResponse> getFlightByStage(FlightStage flightStage);
    FlightResponse getOngoing();
    FlightResponse getNewestFlight();
    List<SimpleFlightResponse> getAllFlightsBasicData();
}
