package pl.edu.pk.cosmo.habsatbackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pk.cosmo.habsatbackend.converter.FlightConverter;
import pl.edu.pk.cosmo.habsatbackend.entity.assets.FlightStage;
import pl.edu.pk.cosmo.habsatbackend.entity.request.NewFlightRequest;
import pl.edu.pk.cosmo.habsatbackend.entity.response.FlightResponse;
import pl.edu.pk.cosmo.habsatbackend.entity.response.SimpleFlightResponse;
import pl.edu.pk.cosmo.habsatbackend.exception.FlightAlreadyExistsException;
import pl.edu.pk.cosmo.habsatbackend.exception.NoFlightException;
import pl.edu.pk.cosmo.habsatbackend.repository.FlightRepository;
import pl.edu.pk.cosmo.habsatbackend.service.FlightService;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;
    private final FlightConverter flightConverter;

    @Override
    public FlightResponse getFlightByDate(LocalDate date) throws NoFlightException {
        return flightRepository.findFlightByDate(date)
                .map(flightConverter::responeOf)
                .orElseThrow(
                        () -> new NoFlightException("There is no filght with date: " + date.toString())
                );
    }

    @Override
    public void createNewFlight(NewFlightRequest newFlightRequest) throws FlightAlreadyExistsException {
        final LocalDate date = newFlightRequest.getDate();

        if(flightRepository.existsByDate(date)) {
            throw new FlightAlreadyExistsException("There has already been flight at " + date.toString());
        }

        flightRepository.save(flightConverter.toFlight(newFlightRequest));
    }

    @Override
    public List<FlightResponse> getFlightByStage(FlightStage flightStage) {
        return flightRepository.findFlightByFlightStage(flightStage)
                .stream()
                .map(flightConverter::responeOf)
                .collect(Collectors.toList());
    }

    @Override
    public FlightResponse getOngoing() {
        return flightRepository.findOngoing()
                .map(flightConverter::responeOf)
                .orElse(null);
    }

    @Override
    public FlightResponse getNewestFlight() {
        List<FlightResponse> list = this.getFlightByStage(FlightStage.ONGOING);

        if(list.size() < 0)
            return getFlightByStage(FlightStage.FINISHED)
                    .stream()
                    .min(Comparator.comparing(FlightResponse::getDate))
                    .orElse(null);

        return getFlightByStage(FlightStage.ONGOING).get(0);
    }

    @Override
    public List<SimpleFlightResponse> getAllFlightsBasicData() {
        return flightRepository.findAll().stream()
                .map(flightConverter::simpleResponseOf)
                .collect(Collectors.toList());
    }
}
