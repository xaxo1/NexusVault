package com.nexusvault.mspayments.service;

import reactor.core.publisher.Mono;
import java.math.BigDecimal;

/**
 * Interfaz encargada de definir los mecanismos de comunicación asíncrona hacia microservicios externos.
 * Es vital para sincronizar información financiera sin bloquear hilos.
 */
public interface PaymentCommunicationService {
    /**
     * Ejecuta una petición remota de forma asíncrona para depositar saldo en la billetera del usuario.
     *
     * @param userId Identificador único del usuario destino.
     * @param montoAbonado Monto o importe a transferir.
     * @return Un Mono indicando si el flujo fue exitoso (true) o si se mitigó por fallo de red (false).
     */
    Mono<Boolean> sincronizarFondosConBilleteraAsync(Long userId, BigDecimal montoAbonado);
}