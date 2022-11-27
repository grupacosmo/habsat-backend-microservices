package pl.edu.pk.cosmo.habsatbackend.gateway.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import pl.edu.pk.cosmo.habsatbackend.gateway.service.AuthService;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.web.server.SecurityWebFiltersOrder.AUTHORIZATION;

@Slf4j
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthService authService;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange()
                .anyExchange().permitAll()
                .and()
                .httpBasic()
                .and().formLogin()
                .and()
                .addFilterBefore(new AuthenticationFilter(authService), AUTHORIZATION);

        return http.build();
    }
}