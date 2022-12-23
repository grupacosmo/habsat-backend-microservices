package pl.edu.pk.cosmo.habsatbackend.service.impl;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import pl.edu.pk.cosmo.habsatbackend.entity.Flight;
import pl.edu.pk.cosmo.habsatbackend.entity.FlightData;
import pl.edu.pk.cosmo.habsatbackend.entity.assets.FlightStage;
import pl.edu.pk.cosmo.habsatbackend.exception.NoDataException;
import pl.edu.pk.cosmo.habsatbackend.repository.DataRepository;
import pl.edu.pk.cosmo.habsatbackend.repository.FlightRepository;
import pl.edu.pk.cosmo.habsatbackend.service.DataService;
import pl.edu.pk.cosmo.habsatbackend.service.FlightService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class DataServiceImpl implements DataService {
    private final DataRepository dataRepository;
    private final FlightRepository flightRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final JdbcTemplate jdbcTemplate;

    private final Integer FLIGHTID_TEST_LOCATOR = 99;

    @Override
    public void save(final FlightData flightData) {
        dataRepository.save(flightData);
    }

    @Override
    @Transactional
    public void handleRetrievedMessage(final FlightData flightData) {
       final Integer flightId = flightRepository.findOngoing()
               .map(Flight::getFlightId)
               .orElse(FLIGHTID_TEST_LOCATOR);

        jdbcTemplate.update("INSERT INTO data_test(SPEED, ALTITUDE, LONGITUDE, LATITUDE,TEMPERATURE, TIME, RSSI, FLIGHT_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                flightData.getSpeed(),
                flightData.getAltitude(),
                flightData.getLongitude(),
                flightData.getLatitude(),
                flightData.getTemperature(),
                flightData.getTime(),
                flightData.getRssi(),
                flightId);
        simpMessagingTemplate.convertAndSend("/data/ws", flightData);
    }

    @Override
    public List<FlightData> findAll() {
        return dataRepository.findAll();
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.execute("ALTER TABLE DATA_TEST ALTER COLUMN ID RESTART WITH 1");
        dataRepository.deleteAll();
    }

    @Override
    public void saveAll(List<FlightData> list) {
        dataRepository.saveAll(list);
    }

    @Override
    public void changeData(FlightData newFlightData, Long id) throws NoDataException {

        FlightData flightData = dataRepository.findById(id).map((data) -> {
            return data.setTemperature(newFlightData.getTemperature() != null ? newFlightData.getTemperature() : data.getTemperature())
                    .setAltitude(newFlightData.getAltitude() != null ? newFlightData.getAltitude() : data.getAltitude())
                    .setLongitude(newFlightData.getLongitude() != null ? newFlightData.getLongitude() : data.getLongitude())
                    .setLatitude(newFlightData.getLatitude() != null ? newFlightData.getLatitude() : data.getLatitude())
                    .setRssi(newFlightData.getRssi() != null ? newFlightData.getRssi() : data.getRssi())
                    .setSpeed(newFlightData.getSpeed() != null ? newFlightData.getSpeed() : data.getSpeed())
                    .setTime(newFlightData.getTime() != null ? newFlightData.getTime() : data.getTime());
        }).orElseThrow(() -> new NoDataException("There is no data with given id: " + id));


        dataRepository.save(flightData);
    }

    @Override
    public FlightData findById(Long id) throws NoDataException {
        return dataRepository.findById(id)
                .orElseThrow(() -> new NoDataException("There is no data with given id: " + id));
    }

    @Override
    public void deleteById(Long id) throws NoDataException {
        if(!dataRepository.existsById(id))
            throw new NoDataException("There is no data with given id: " + id);

        dataRepository.deleteById(id);
    }

    @Override
    public void deleteTestData() {
        dataRepository.deleteAllByFlight_id(FLIGHTID_TEST_LOCATOR);
    }
}
