package com.nexusvault.msreports.service.impl;

import com.nexusvault.msreports.dto.OrderRemoteDTO;
import com.nexusvault.msreports.dto.UserRemoteDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReportClient {

    private final WebClient webClient;

    // Inyección explícita por constructor para cumplir con la rúbrica
    public ReportClient(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Consulta de forma asíncrona el perfil del usuario en ms-users
     */
    public Mono<UserRemoteDTO> obtenerPerfilUsuarioAsync(Long userId) {
        return webClient.get()
            .uri("http://localhost:8076/api/users/profiles/auth/{id}", userId)
            .retrieve()
            .bodyToMono(UserRemoteDTO.class)
            .onErrorResume(error -> {
                System.err.println("Resiliencia ReportClient: Error al obtener usuario. Motivo: " + error.getMessage());
                return Mono.empty(); // Retorna vacío si falla para no romper todo el reporte
            });
    }

    /**
     * Consulta el historial de órdenes en ms-orders y lo consolida en una Lista
     */
    public Mono<List<OrderRemoteDTO>> obtenerOrdenesUsuarioAsync(Long userId) {
        return webClient.get()
            .uri("http://localhost:8083/api/v1/orders/user/{id}", userId)
            .retrieve()
            .bodyToFlux(OrderRemoteDTO.class)
            .collectList() // Convierte el Flux en un Mono<List> tal como en tu archivo guía
            .onErrorReturn(new ArrayList<>()); // Resiliencia declarativa: si ms-orders falla, devuelve lista vacía
    }
}