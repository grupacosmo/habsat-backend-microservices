package pl.edu.pk.cosmo.habsatbackend.converter;

import org.springframework.stereotype.Component;
import pl.edu.pk.cosmo.habsatbackend.entity.FlightData;
import pl.edu.pk.cosmo.habsatbackend.entity.response.FlightDataResponse;

@Component
public class DataConverter {

    public FlightDataResponse responseOf(FlightData flightData) {
        return new FlightDataResponse()
                .setRssi(flightData.getRssi())
                .setAltitude(flightData.getAltitude())
                .setLatitude(flightData.getLatitude())
                .setLongitude(flightData.getLongitude())
                .setSpeed(flightData.getSpeed())
                .setTemperature(flightData.getTemperature());
    }
}
