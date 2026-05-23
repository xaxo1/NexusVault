package com.nexusvault.msreports.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {

    /*
     * [CONFIGURACIÓN DE ARQUITECTURA REACTIVA DE AGREGACIÓN]
     * Registramos el Bean único de WebClient. En este microservicio,
     * nos permitirá orquestar múltiples peticiones simultáneas hacia otros 
     * servicios del ecosistema para consolidar estadísticas e informes masivos.
     */
    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}