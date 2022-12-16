package pl.edu.pk.cosmo.habsatbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.edu.pk.cosmo.habsatbackend.entity.assets.FlightStage;
import pl.edu.pk.cosmo.habsatbackend.entity.request.NewFlightRequest;
import pl.edu.pk.cosmo.habsatbackend.entity.response.FlightResponse;
import pl.edu.pk.cosmo.habsatbackend.entity.response.SimpleFlightResponse;
import pl.edu.pk.cosmo.habsatbackend.exception.FlightAlreadyExistsException;
import pl.edu.pk.cosmo.habsatbackend.exception.NoFlightException;
import pl.edu.pk.cosmo.habsatbackend.service.FlightService;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/flight")
@RestController
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @GetMapping
    public List<SimpleFlightResponse> getFlightsSimpleData() {
        return flightService.getAllFlightsBasicData();
    }

    @GetMapping("{date}")
    public FlightResponse getFlightById(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        try {
            return flightService.getFlightByDate(date);
        } catch (NoFlightException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("{date}/flightData")
    public FlightResponse getFlightDataByDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        try {
            return flightService.getFlightByDate(date);
        } catch (NoFlightException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("newest")
    @ResponseStatus(HttpStatus.OK)
    public FlightResponse getNewestFlight() {
        return flightService.getNewestFlight();
    }

    @GetMapping("stage/ONGOING")
    public FlightResponse getFlightByStage() {
        return flightService.getOngoing();
    }

    @GetMapping("stage/{stage}")
    public List<FlightResponse> getFlightByStage(@PathVariable FlightStage stage) {
        return flightService.getFlightByStage(stage);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void planNewFlight(@RequestBody NewFlightRequest newFlightRequest) {
        try {
            flightService.createNewFlight(newFlightRequest);
        } catch (FlightAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
