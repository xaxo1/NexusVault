package com.nexusvault.msorders.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {

    /*
     * [CONFIGURACIÓN DE ARQUITECTURA REACTIVA CENTRALIZADA]
     * Registramos WebClient como un Bean único e inmutable en el contenedor de Spring IOC.
     * Esto nos permite inyectarlo mediante constructor en las capas de servicio para
     * gatillar transferencias de datos asíncronas y eficientes con el resto del ecosistema.
     */
    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}