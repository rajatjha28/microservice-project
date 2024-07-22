package com.example.hotel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        security
                .authorizeRequests(authorize -> authorize.anyRequest().authenticated())
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer
                        .jwt(withDefaults())
                );
        return security.build();
    }
}
