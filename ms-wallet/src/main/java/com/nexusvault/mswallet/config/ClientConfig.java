package com.nexusvault.mswallet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {

    /*
     * [CONFIGURACIÓN DE INFRAESTRUCTURA REACTIVA]
     * Registramos WebClient en el contenedor IOC de Spring.
     * Nos servirá para disparar avisos asíncronos y no bloqueantes hacia 
     * otros microservicios (como ms-notifications) tras operaciones financieras.
     */
    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}