package pl.edu.pk.cosmo.habsatbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.edu.pk.cosmo.habsatbackend.entity.FlightData;
import pl.edu.pk.cosmo.habsatbackend.entity.response.FlightResponse;
import pl.edu.pk.cosmo.habsatbackend.exception.NoFlightException;
import pl.edu.pk.cosmo.habsatbackend.service.FlightService;

import java.util.List;

@RequestMapping("/flight")
@RestController
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @GetMapping("{id}")
    public FlightResponse getFlightById(@PathVariable Integer id) {
        try {
            return flightService.getFlightById(id);
        } catch (NoFlightException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("{id}/flightData")
    public List<FlightData> getFlightDataById(@PathVariable Integer id) {
        try {
            return flightService.getFlightDataListById(id);
        } catch (NoFlightException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
