package pl.edu.pk.cosmo.habsatbackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pk.cosmo.habsatbackend.converter.FlightConverter;
import pl.edu.pk.cosmo.habsatbackend.entity.FlightData;
import pl.edu.pk.cosmo.habsatbackend.entity.assets.FlightStage;
import pl.edu.pk.cosmo.habsatbackend.entity.request.NewFlightRequest;
import pl.edu.pk.cosmo.habsatbackend.entity.response.FlightResponse;
import pl.edu.pk.cosmo.habsatbackend.exception.FlightAlreadyExistsException;
import pl.edu.pk.cosmo.habsatbackend.exception.NoFlightException;
import pl.edu.pk.cosmo.habsatbackend.repository.FlightRepository;
import pl.edu.pk.cosmo.habsatbackend.service.FlightService;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;
    private final FlightConverter flightConverter;

    @Override
    public FlightResponse getFlightByDate(Date date) throws NoFlightException {
        return flightRepository.findFlightByDate(date)
                .map(flightConverter::responeOf)
                .orElseThrow(
                        () -> new NoFlightException("There is no filght with date: " + date.toString())
                );
    }

    @Override
    public List<FlightData> getFlightDataListById(Integer id) throws NoFlightException {
        if(!flightRepository.existsById(id)) {
            throw new NoFlightException("There is no filght with id: " + id);
        }

        return flightRepository.findById(id)
                .get()
                .getFlightDataList()
                .stream()
                .sorted(Comparator.comparing(FlightData::getTime)).collect(Collectors.toList());
    }

    @Override
    public void createNewFlight(NewFlightRequest newFlightRequest) throws FlightAlreadyExistsException {
        final Date date = newFlightRequest.getDate();

        if(flightRepository.existsByDate(date)) {
            throw new FlightAlreadyExistsException("There has already been flight at " + date.toString());
        }

        flightRepository.save(flightConverter.toFlight(newFlightRequest));
    }

    @Override
    public FlightResponse getFlightByStage(FlightStage flightStage) {

        if(flightStage.equals(FlightStage.FINISHED)) {
            // throw NotSupportedStageException
        }

        return flightRepository.findFlightByFlightStage(flightStage)
                .map(flightConverter::responeOf)
                .orElse(null);
    }

    @Override
    public FlightResponse getNewestFlight() {
        return Optional.ofNullable(getFlightByStage(FlightStage.ONGOING))
                .orElse(
                        flightRepository.findMostRecentFlight()
                                .map(flightConverter::responeOf)
                                .orElse(null)
                );
    }
}
