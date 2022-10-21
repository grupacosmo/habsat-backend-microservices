package pl.edu.pk.cosmo.habsatbackend.configuration;

import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import pl.edu.pk.cosmo.habsatbackend.entity.FlightData;
import pl.edu.pk.cosmo.habsatbackend.service.DataService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class MQTTConfiguration {

    DataService dataService;

    public MQTTConfiguration(DataService dataService) {
        this.dataService = dataService;
    }

    @Bean
    public MqttPahoClientFactory mqttPahoClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();

        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[] {"tcp://eu1.cloud.thethings.network:1883"});
        options.setUserName("lora-e5-v2@ttn");
        options.setCleanSession(true);
        options.setPassword("NNSXS.G7OGWFKZ6FZKFZLAQHK5QMADLIS3HVT5DTL7FDA.U54MKSS5HNEDOFZS24PJASKT6ACTKPABP43LE3OLA26GQW4YLIWQ".toCharArray());
        factory.setConnectionOptions(options);

        return factory;
    }

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter("serverIn", mqttPahoClientFactory(),
                        "v3/lora-e5-v2@ttn/devices/eui-2cf7f1203230918a/up");
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(2);
        adapter.setOutputChannel(mqttInputChannel());

        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return new MessageHandler() {

            @SneakyThrows
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                String mess = message.getPayload().toString();
                ArrayList<String> arr = new ArrayList<>(List.of(mess.split("\"time\":\"")));
                //String time = arr.get(1).split("\"");
                ArrayList<String> rssiArr = new ArrayList<>(List.of(mess.split("\"rssi\":")));
                String rssi = rssiArr.get(1).split(",")[0];
                ArrayList<String> dataArr = new ArrayList<>(List.of(mess.split("\"text\":\"")));
                String data = dataArr.get(1).split("\"")[0];
                ArrayList<String> mainData = new ArrayList<>(List.of(data.split(";")));

                /*
                "time":"2022-04-03T12:34:16.373069Z"
                "rssi":-51
                "decoded_payload":{"text":"209.85;50.08;20.03;1.22;18.10"}
                 */
                final FlightData flightDataToDb = new FlightData();
                flightDataToDb.setAltitude(Double.valueOf(mainData.get(0).substring(0)));
                flightDataToDb.setLatitude(Double.valueOf(mainData.get(1)));
                flightDataToDb.setLongitude(Double.valueOf(mainData.get(2)));
                flightDataToDb.setSpeed(Double.valueOf(mainData.get(3).substring(0, mainData.get(4).length()-1)));
                flightDataToDb.setTemperature(Double.valueOf(mainData.get(4)));
                flightDataToDb.setRssi(Double.valueOf(rssi));
                flightDataToDb.setTime(LocalDateTime.now());

                System.out.println(flightDataToDb);
                dataService.sendFrame(flightDataToDb);
            }
        };
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler("serverOut", mqttPahoClientFactory());

        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic("v3/lora-e5-v2@ttn/devices/eui-2cf7f1203230918a/up"); //"v3/lora-e5-v2@ttn/devices/eui-2cf7f1203230918a/up"
        return messageHandler;
    }

}
