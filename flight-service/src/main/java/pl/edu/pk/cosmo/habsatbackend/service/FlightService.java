package pl.edu.pk.cosmo.habsatbackend.service;

import pl.edu.pk.cosmo.habsatbackend.entity.Flight;
import pl.edu.pk.cosmo.habsatbackend.entity.FlightData;
import pl.edu.pk.cosmo.habsatbackend.entity.assets.FlightStage;
import pl.edu.pk.cosmo.habsatbackend.entity.request.NewFlightRequest;
import pl.edu.pk.cosmo.habsatbackend.entity.response.FlightResponse;
import pl.edu.pk.cosmo.habsatbackend.exception.FlightAlreadyExistsException;
import pl.edu.pk.cosmo.habsatbackend.exception.NoFlightException;

import java.util.Date;
import java.util.List;


public interface FlightService {
    FlightResponse getFlightByDate(Date date) throws NoFlightException;
    List<FlightData> getFlightDataListById(Integer id) throws NoFlightException;
    void createNewFlight(NewFlightRequest newFlightRequest) throws FlightAlreadyExistsException;
    FlightResponse getFlightByStage(FlightStage flightStage);
    FlightResponse getNewestFlight();
}
