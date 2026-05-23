package com.nexusvault.mspayments.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {

    /*
     * [CONFIGURACIÓN DE LA ARQUITECTURA REACTIVA DE COMUNICACIÓN]
     * Registramos el Bean inmutable de WebClient para inyectarlo por constructor.
     * Este componente nos permitirá disparar flujos asíncronos hacia ms-wallet
     * o ms-orders de manera no bloqueante.
     */
    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}