package pl.edu.pk.cosmo.habsatbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pk.cosmo.habsatbackend.converter.FlightConverter;
import pl.edu.pk.cosmo.habsatbackend.entity.FlightData;
import pl.edu.pk.cosmo.habsatbackend.entity.response.FlightResponse;
import pl.edu.pk.cosmo.habsatbackend.exception.NoFlightException;
import pl.edu.pk.cosmo.habsatbackend.repository.FlightRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;
    private final FlightConverter flightConverter;

    public FlightResponse getFlightById(Integer id) throws NoFlightException {
        if(!flightRepository.existsById(id)) {
            throw new NoFlightException("There is no filght with id: " + id);
        }

        return flightConverter.convertFlightToFlightResponse(
                flightRepository.findById(id).get());
    }

    public List<FlightData> getFlightDataListById(Integer id) throws NoFlightException {
        if(!flightRepository.existsById(id)) {
            throw new NoFlightException("There is no filght with id: " + id);
        }

        return flightRepository.findById(id)
                .get()
                .getFlightDataList()
                .stream()
                .sorted(new Comparator<FlightData>() {
                    @Override
                    public int compare(FlightData o1, FlightData o2) {
                        return o1.getTime().compareTo(o2.getTime());
                    }
                }).collect(Collectors.toList());
    }
}
