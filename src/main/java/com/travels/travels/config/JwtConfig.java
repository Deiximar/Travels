package com.travels.travels.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.travels.travels.security.JwtUtil;
import com.travels.travels.services.CustomUserDetailsService;

@Configuration
public class JwtConfig {
     @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {
        return new JwtAuthenticationFilter(jwtUtil, customUserDetailsService);
}
}