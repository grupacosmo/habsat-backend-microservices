package pl.edu.pk.cosmo.habsatbackend.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import pl.edu.pk.cosmo.habsatbackend.entity.FlightData;
import pl.edu.pk.cosmo.habsatbackend.entity.response.FlightDataResponse;

import java.time.LocalDateTime;
import java.util.List;

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

    public String beufify(String message) {
        return message.replace("\"", "");
    }
    public FlightData dataOf(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(message);

        JsonNode uplinkNode = jsonNode.get("data").get("uplink_message");
        JsonNode rxMetadataNode = uplinkNode.get("rx_metadata").get(0);


        // Modify entity to retrieve this parameters
        String rssi = rxMetadataNode.get("rssi").toString();
        String snr = rxMetadataNode.get("snr").toString();
        String channelIndex = rxMetadataNode.get("channel_index").toString();
        String consumedAirtime = uplinkNode.get("consumed_airtime").toString();
        String spreadingFactor = uplinkNode.get("settings").get("data_rate").get("lora").get("spreading_factor").toString();
        String payload = uplinkNode.get("decoded_payload").get("text").toString();

        List<String> mainData = List.of(beufify(payload).split(";"));

        return new FlightData().setAltitude(Double.valueOf(mainData.get(0)))
                    .setLatitude(Double.valueOf(mainData.get(1)))
                    .setLongitude(Double.valueOf(mainData.get(2)))
                    .setSpeed(Double.valueOf(mainData.get(3).substring(0, mainData.get(4).length()-1)))
                    .setTemperature(Double.valueOf(mainData.get(4)))
                    .setRssi(Double.valueOf(rssi))
                    .setTime(LocalDateTime.now())
                    .setFlight_id(1);

    }
}
