package pl.edu.pk.cosmo.habsatbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.edu.pk.cosmo.habsatbackend.entity.FlightData;
import pl.edu.pk.cosmo.habsatbackend.exception.NoDataException;
import pl.edu.pk.cosmo.habsatbackend.service.DataService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/data-frame")
public class DataController {

    final DataService dataService;

    public DataController(final DataService dataService) {
        this.dataService = dataService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid final FlightData flightData) {
            dataService.save(flightData);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/list")
    public ResponseEntity<?> saveAll(@RequestBody List<FlightData> list) {
            dataService.saveAll(list);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> changeData(@RequestBody final FlightData newFlightData, @PathVariable Long id) {
        try {
            dataService.changeData(newFlightData, id);
            return ResponseEntity.ok().build();
        } catch (NoDataException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping()
    public List<FlightData> findAll() {
        return dataService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightData> findById(@PathVariable final Long id) {
        try {
            return ResponseEntity.ok(dataService.findById(id));
        } catch (NoDataException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

//    @DeleteMapping("/~~deleteAll__webdev__access_265")
//    public ResponseEntity<?> delete() {
//        dataService.deleteAll();
//        return ResponseEntity.ok().build();
//    }
//
//    @DeleteMapping("/~~deleteAll__webdev__access_265/{id}")
//    public ResponseEntity<?> deleteById(@PathVariable final Long id) {
//        try {
//            dataService.deleteById(id);
//            return ResponseEntity.ok().build();
//        } catch (NoDataException e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
//        }
//    }

}
