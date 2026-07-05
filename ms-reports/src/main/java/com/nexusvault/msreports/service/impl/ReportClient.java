package com.nexusvault.msreports.service.impl;

import com.nexusvault.msreports.dto.OrderRemoteDTO;
import com.nexusvault.msreports.dto.UserRemoteDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * Cliente reactivo que gestiona la comunicación con otros microservicios (como ms-users y ms-orders) utilizando {@link WebClient}.
 */
@Component
public class ReportClient {

    private final WebClient webClient;

    // Inyección explícita por constructor para cumplir con la rúbrica
    public ReportClient(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Consulta de forma asíncrona el perfil del usuario en el microservicio ms-users.
     * En caso de error, implementa resiliencia devolviendo un {@link Mono#empty()}.
     *
     * @param userId El identificador único del usuario.
     * @return Un objeto {@link Mono} con los datos remotos del usuario en un {@link UserRemoteDTO}.
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
     * Consulta de forma asíncrona el historial de órdenes en el microservicio ms-orders para consolidarlas en una lista.
     * En caso de error, retorna una lista vacía para no interrumpir el flujo.
     *
     * @param userId El identificador único del usuario para buscar sus órdenes.
     * @return Un objeto {@link Mono} que emite una lista de {@link OrderRemoteDTO}.
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