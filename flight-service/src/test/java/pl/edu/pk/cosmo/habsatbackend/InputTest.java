package pl.edu.pk.cosmo.habsatbackend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.edu.pk.cosmo.habsatbackend.converter.DataConverter;
import pl.edu.pk.cosmo.habsatbackend.entity.FlightData;

import java.time.LocalDateTime;

public class InputTest {
    private static final String test = "{" +
            "  \"name\": \"as.up.data.forward\"," +
            "  \"time\": \"2022-11-23T18:22:47.240324976Z\"," +
            "  \"identifiers\": [" +
            "    {" +
            "      \"device_ids\": {" +
            "        \"device_id\": \"eui-2cf7f1203230810c\"," +
            "        \"application_ids\": {" +
            "          \"application_id\": \"lora-e5-mini-cosmo\"" +
            "        }," +
            "        \"dev_eui\": \"2CF7F1203230810C\"," +
            "        \"join_eui\": \"8000000000000006\"," +
            "        \"dev_addr\": \"260B871D\"" +
            "      }" +
            "    }" +
            "  ]," +
            "  \"data\": {" +
            "    \"@type\": \"type.googleapis.com/ttn.lorawan.v3.ApplicationUp\"," +
            "    \"end_device_ids\": {" +
            "      \"device_id\": \"eui-2cf7f1203230810c\"," +
            "      \"application_ids\": {" +
            "        \"application_id\": \"lora-e5-mini-cosmo\"" +
            "      }," +
            "      \"dev_eui\": \"2CF7F1203230810C\"," +
            "      \"join_eui\": \"8000000000000006\"," +
            "      \"dev_addr\": \"260B871D\"" +
            "    }," +
            "    \"correlation_ids\": [" +
            "      \"as:up:01GJJVMSE4WM725RZ20Z7634HP\"," +
            "      \"gs:conn:01GJJRCVZDTEEHTDM52JX6KR68\"," +
            "      \"gs:up:host:01GJJRCVZK52Q621HDXX1YX9BG\"," +
            "      \"gs:uplink:01GJJVMS7KS0G23GJ2M2QKX5EM\"," +
            "      \"ns:uplink:01GJJVMS7MBCDNG3FK9F15D36S\"," +
            "      \"rpc:/ttn.lorawan.v3.GsNs/HandleUplink:01GJJVMS7K47GMC03SESH9REWR\"," +
            "      \"rpc:/ttn.lorawan.v3.NsAs/HandleUplink:01GJJVMSE3AX0DQ076PEM2EM1W\"" +
            "    ]," +
            "    \"received_at\": \"2022-11-23T18:22:47.235515269Z\"," +
            "    \"uplink_message\": {" +
            "      \"session_key_id\": \"AYSloN6Pn0ZXk17j1mgW1g==\"," +
            "      \"f_port\": 8," +
            "      \"f_cnt\": 27," +
            "      \"frm_payload\": \"TUM0d01Ec3dMakF3TURBN01DNHdNREF3T3pBdU1EQTdNall1TkRFPQ==\"," +
            "      \"decoded_payload\": {" +
            "        \"text\": \"0.00;0.0000;0.0000;0.00;26.41\"" +
            "      }," +
            "      \"rx_metadata\": [" +
            "        {" +
            "          \"gateway_ids\": {" +
            "            \"gateway_id\": \"cosmo-gateway\"," +
            "            \"eui\": \"B827EBFFFE59EC60\"" +
            "          }," +
            "          \"time\": \"2022-11-23T18:22:47.004964Z\"," +
            "          \"timestamp\": 3407472572," +
            "          \"rssi\": -61," +
            "          \"channel_rssi\": -61," +
            "          \"snr\": 10.2," +
            "          \"uplink_token\": \"ChsKGQoNY29zbW8tZ2F0ZXdheRIIuCfr//5Z7GAQvM/n2AwaCwj3yfmbBhDNg/oMIODsxOmVYw==\"," +
            "          \"channel_index\": 6," +
            "          \"received_at\": \"2022-11-23T18:22:47.027165133Z\"" +
            "        }" +
            "      ]," +
            "      \"settings\": {" +
            "        \"data_rate\": {" +
            "          \"lora\": {" +
            "            \"bandwidth\": 125000," +
            "            \"spreading_factor\": 7," +
            "            \"coding_rate\": \"4/5\"" +
            "          }" +
            "        }," +
            "        \"frequency\": \"867700000\"," +
            "        \"timestamp\": 3407472572," +
            "        \"time\": \"2022-11-23T18:22:47.004964Z\"" +
            "      }," +
            "      \"received_at\": \"2022-11-23T18:22:47.028036033Z\"," +
            "      \"consumed_airtime\": \"0.102656s\"," +
            "      \"network_ids\": {" +
            "        \"net_id\": \"000013\"," +
            "        \"tenant_id\": \"ttn\"," +
            "        \"cluster_id\": \"eu1\"," +
            "        \"cluster_address\": \"eu1.cloud.thethings.network\"" +
            "      }" +
            "    }" +
            "  }," +
            "  \"correlation_ids\": [" +
            "    \"as:up:01GJJVMSE4WM725RZ20Z7634HP\"," +
            "    \"gs:conn:01GJJRCVZDTEEHTDM52JX6KR68\"," +
            "    \"gs:up:host:01GJJRCVZK52Q621HDXX1YX9BG\"," +
            "    \"gs:uplink:01GJJVMS7KS0G23GJ2M2QKX5EM\"," +
            "    \"ns:uplink:01GJJVMS7MBCDNG3FK9F15D36S\"," +
            "    \"rpc:/ttn.lorawan.v3.GsNs/HandleUplink:01GJJVMS7K47GMC03SESH9REWR\"," +
            "    \"rpc:/ttn.lorawan.v3.NsAs/HandleUplink:01GJJVMSE3AX0DQ076PEM2EM1W\"" +
            "  ]," +
            "  \"origin\": \"ip-10-100-7-94.eu-west-1.compute.internal\"," +
            "  \"context\": {" +
            "    \"tenant-id\": \"CgN0dG4=\"" +
            "  }," +
            "  \"visibility\": {" +
            "    \"rights\": [" +
            "      \"RIGHT_APPLICATION_TRAFFIC_READ\"" +
            "    ]" +
            "  }," +
            "  \"unique_id\": \"01GJJVMSE86H43G9E7PPDZBW9V\"" +
            "}" +
            "";

    public static void main(String[] args) throws JsonProcessingException {
        System.out.println(test);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(test);

        JsonNode uplinkNode = jsonNode.get("data").get("uplink_message");
        JsonNode rxMetadataNode = uplinkNode.get("rx_metadata").get(0);

        System.out.println(rxMetadataNode.get("rssi").toString());
        System.out.println(rxMetadataNode.get("snr").toString());
        System.out.println(rxMetadataNode.get("channel_index").toString());

        System.out.println(uplinkNode.get("consumed_airtime").toString());

        System.out.println(uplinkNode.get("settings").get("data_rate").get("lora").get("spreading_factor").toString());

        String mainData = uplinkNode.get("decoded_payload").get("text").toString();

        final FlightData flightDataToDb = new FlightData();

        DataConverter dataConverter = new DataConverter();

        System.out.println(dataConverter.dataOf(test));
    }
}
