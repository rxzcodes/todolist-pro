package com.portfolio.todolist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // Permite credenciais (cookies, headers de autenticação)
        config.setAllowCredentials(true);

        // Origens permitidas (adicione aqui os URLs do seu frontend)
        config.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",  // React, Vue padrão
                "http://localhost:4200",  // Angular padrão
                "http://localhost:5173",  // Vite padrão
                "http://localhost:8081"   // Outra porta comum
        ));

        // Headers permitidos
        config.setAllowedHeaders(List.of("*"));

        // Métodos HTTP permitidos
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

        // Headers expostos na resposta
        config.setExposedHeaders(Arrays.asList("Authorization", "Content-Type"));

        // Tempo de cache da configuração CORS (1 hora)
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Aplica a todos os endpoints

        return new CorsFilter(source);
    }
}