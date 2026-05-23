package com.nexusvault.msorders.service.impl;

import com.nexusvault.msorders.dto.SkinRemoteDTO;
import com.nexusvault.msorders.dto.WalletRemoteDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class OrderOrchestratorService {

    private final WebClient webClient;

    // Inyección explícita por constructor para garantizar el aislamiento de dependencias
    public OrderOrchestratorService(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * [FLUJO ASÍNCRONO ENCADENADO - EXIGENCIA DE RÚBRICA]
     * Orquesta la validación completa de una orden: Consulta catálogo, verifica fondos
     * y gatilla mitigación de errores declarativa (Resiliencia) si un servicio secundario falla.
     */
    public Mono<Boolean> validarYProcesarOrdenAsync(Long userId, Long skinId) {
        
        // FASE 1: Consultar datos de la Skin en ms-catalog
        return webClient.get()
            .uri("http://localhost:8083/api/catalog/skins/{id}", skinId) // URL de tu ms-catalog
            .retrieve()
            .bodyToMono(SkinRemoteDTO.class)
            .flatMap(skin -> {
                
                // Regla de negocio local: Si la skin no está disponible, abortamos inmediatamente
                if (!skin.disponible()) {
                    return Mono.just(false);
                }

                // FASE 2: Encadenar llamada para verificar la billetera del usuario en ms-wallet
                return webClient.get()
                    .uri("http://localhost:8084/api/wallet/user/{userId}", userId) // URL de tu ms-wallet
                    .retrieve()
                    .bodyToMono(WalletRemoteDTO.class)
                    // RESILIENCIA DECLARATIVA: Si ms-wallet está caído, devolvemos un objeto de contingencia seguro
                    .onErrorReturn(new WalletRemoteDTO(null, userId, BigDecimal.ZERO, false))
                    .flatMap(wallet -> {
                        
                        // Verificar si tiene saldo suficiente para la skin seleccionada
                        if (wallet.isActive() && wallet.saldoActual().compareTo(skin.precio()) >= 0) {
                            
                            // FASE 3: Si todo es válido, procedemos a realizar el cobro (POST) en ms-wallet
                            var cobroPayload = Map.of(
                                "userId", userId,
                                "amount", skin.precio()
                            );

                            return webClient.post()
                                .uri("http://localhost:8084/api/wallet/pay")
                                .bodyValue(cobroPayload)
                                .retrieve()
                                .bodyToMono(String.class)
                                .map(respuesta -> true) // Transacción e intento de cobro exitosos
                                .onErrorReturn(false);  // Si el cobro falla por red, mitiga retornando falso
                        }
                        
                        return Mono.just(false); // Saldo insuficiente
                    });
            })
            // RESILIENCIA GLOBAL: Si el catálogo o toda la cadena colapsa, capturamos el error para no congelar el microservicio
            .onErrorResume(error -> {
                System.err.println("CRÍTICO - Resiliencia activada en ms-orders: Fallo en la comunicación. Motivo: " + error.getMessage());
                return Mono.just(false); // Fallo mitigado devolviendo estado controlado
            });
    }
}