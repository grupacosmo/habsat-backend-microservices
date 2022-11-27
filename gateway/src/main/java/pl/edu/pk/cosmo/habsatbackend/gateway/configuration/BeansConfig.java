package pl.edu.pk.cosmo.habsatbackend.gateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeansConfig {
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
