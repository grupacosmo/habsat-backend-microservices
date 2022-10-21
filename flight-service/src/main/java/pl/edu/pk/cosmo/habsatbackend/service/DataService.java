package pl.edu.pk.cosmo.habsatbackend.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import pl.edu.pk.cosmo.habsatbackend.entity.FlightData;
import pl.edu.pk.cosmo.habsatbackend.exception.NoDataException;
import pl.edu.pk.cosmo.habsatbackend.repository.DataRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class DataService {
    final DataRepository dataRepository;
    final SimpMessagingTemplate simpMessagingTemplate;
    final JdbcTemplate jdbcTemplate;
    final static Logger logger = LoggerFactory.getLogger(DataService.class);

    public void save(final FlightData flightData) {
        dataRepository.save(flightData);
    }

    @Transactional
    public void sendFrame(final FlightData flightData) {
        jdbcTemplate.update("INSERT INTO data_test(SPEED, ALTITUDE, LONGITUDE, LATITUDE,TEMPERATURE, TIME, RSSI) VALUES (?, ?, ?, ?, ?, ?, ?)",
                flightData.getSpeed(),
                flightData.getAltitude(),
                flightData.getLongitude(),
                flightData.getLatitude(),
                flightData.getTemperature(),
                flightData.getTime(),
                flightData.getRssi());
        simpMessagingTemplate.convertAndSend("/data/ws", flightData);
    }

    public List<FlightData> findAll() {
        return dataRepository.findAll();
    }

    public void deleteAll() {
        jdbcTemplate.execute("ALTER TABLE DATA ALTER COLUMN ID RESTART WITH 1");
        dataRepository.deleteAll();
    }

    public void saveAll(List<FlightData> list) {
        dataRepository.saveAll(list);
    }

    public void changeData(FlightData newFlightData, Long id) throws NoDataException {
        if(!dataRepository.existsById(id))
            throw new NoDataException("There is no data with given id: " + id);

        FlightData flightData = dataRepository.findById(id).get();
        flightData.setTemperature(newFlightData.getTemperature() != null ? newFlightData.getTemperature() : flightData.getTemperature());
        flightData.setAltitude(newFlightData.getAltitude() != null ? newFlightData.getAltitude() : flightData.getAltitude());
        flightData.setLongitude(newFlightData.getLongitude() != null ? newFlightData.getLongitude() : flightData.getLongitude());
        flightData.setLatitude(newFlightData.getLatitude() != null ? newFlightData.getLatitude() : flightData.getLatitude());
        flightData.setRssi(newFlightData.getRssi() != null ? newFlightData.getRssi() : flightData.getRssi());
        flightData.setSpeed(newFlightData.getSpeed() != null ? newFlightData.getSpeed() : flightData.getSpeed());
        flightData.setTime(newFlightData.getTime() != null ? newFlightData.getTime() : flightData.getTime());

        dataRepository.save(flightData);
    }

    public FlightData findById(Long id) throws NoDataException {
        if(!dataRepository.existsById(id))
            throw new NoDataException("There is no data with given id: " + id);

        return dataRepository.findById(id).get();
    }

    public void deleteById(Long id) throws NoDataException {
        if(!dataRepository.existsById(id))
            throw new NoDataException("There is no data with given id: " + id);

        dataRepository.deleteById(id);
    }
}
