package pbs.edu.cooperative.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterchain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable) // wyłączenie w celu testowania i debugowania, natomiast jest to
                // zabezpieczenie przed atakami na serwer
                .authorizeHttpRequests(auth->auth.anyRequest().authenticated()) // zezwala na autentykacje
                //requesta bez wyjatków
                .httpBasic(Customizer.withDefaults()) //przesyłanie danych autentykacyjnych jako nagłówek http o domyślnej
                //nazwie "Authorisation"
                .build();
    }
}