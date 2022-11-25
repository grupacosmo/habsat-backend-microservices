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
//        options.setUserName("lora-e5-v2@ttn");
//        options.setCleanSession(true);
//        options.setPassword("NNSXS.LDZSHSDYFMS4DDLOS7O2FGW7SMLRH6JR7KZRQOY.XEL2KADMTDK5RORHURE7MFNUK5DUBIV6KMQ3W2WR7SZN2R364PAA".toCharArray());
//        factory.setConnectionOptions(options);

        options.setUserName("lora-e5-mini-cosmo@ttn");
        options.setCleanSession(true);
        options.setPassword("NNSXS.SV47UAWSYTQSZWD3VNZB4QY7XO7KWWD6KDEW53Q.S6D23USGQSJRGWHCDEAUTNYRUSGZHZAIQVAQ7QSQQBK2LBDUFVOA".toCharArray());
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
                        "v3/lora-e5-mini-cosmo@ttn/devices/eui-2cf7f1203230810c/up");
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
                ArrayList<String> rssiArr = new ArrayList<>(List.of(mess.split("\"rssi\":")));
                String rssi = rssiArr.get(1).split(",")[0];
                ArrayList<String> dataArr = new ArrayList<>(List.of(mess.split("\"text\":\"")));
                String data = dataArr.get(1).split("\"")[0];
                ArrayList<String> mainData = new ArrayList<>(List.of(data.split(";")));

                final FlightData flightDataToDb = new FlightData();
                flightDataToDb.setAltitude(Double.valueOf(mainData.get(0).substring(0)));
                flightDataToDb.setLatitude(Double.valueOf(mainData.get(1)));
                flightDataToDb.setLongitude(Double.valueOf(mainData.get(2)));
                flightDataToDb.setSpeed(Double.valueOf(mainData.get(3).substring(0, mainData.get(4).length()-1)));
                flightDataToDb.setTemperature(Double.valueOf(mainData.get(4)));
                flightDataToDb.setRssi(Double.valueOf(rssi));
                flightDataToDb.setTime(LocalDateTime.now());
                flightDataToDb.setFlight_id(1);

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
        messageHandler.setDefaultTopic("v3/lora-e5-mini-cosmo@ttn/devices/eui-2cf7f1203230810c/up");
        return messageHandler;
    }

}
