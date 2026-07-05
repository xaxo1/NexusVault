package com.nexusvault.msreports.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) remoto que representa una orden obtenida del microservicio de órdenes.
 *
 * @param id Identificador de la orden.
 * @param userId Identificador del usuario que realizó la orden.
 * @param skinId Identificador de la skin comprada en esta orden.
 * @param totalPago El monto total pagado en la orden.
 * @param createdAt La fecha y hora de creación de la orden.
 */
public record OrderRemoteDTO(
    Long id,
    Long userId,
    Long skinId,
    BigDecimal totalPago,
    LocalDateTime createdAt
) {}