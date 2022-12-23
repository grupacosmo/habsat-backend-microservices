package pl.edu.pk.cosmo.habsatbackend.configuration;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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
import pl.edu.pk.cosmo.habsatbackend.converter.DataConverter;
import pl.edu.pk.cosmo.habsatbackend.entity.FlightData;
import pl.edu.pk.cosmo.habsatbackend.service.DataService;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MQTTConfiguration {

    private final DataService dataService;
    private final DataConverter dataConverter;

    @Bean
    public MqttPahoClientFactory mqttPahoClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();

        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[] {"tcp://eu1.cloud.thethings.network:1883"});

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

                final FlightData flightDataToDb = dataConverter.dataOf(String.valueOf(message));

                log.info(flightDataToDb.toString());
                dataService.handleRetrievedMessage(flightDataToDb);
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
