package com.nexusvault.mswallet.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

/**
 * DTO que encapsula de forma validada la estructura necesaria para realizar abonos o cargos financieros en una cuenta.
 */
@Schema(description = "Objeto de transferencia para el procesamiento seguro de transacciones de depósito o pago")
@Data
public class TransactionRequestDTO {
    
    @Schema(description = "Identificador único del usuario dueño de la cuenta", example = "3001", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El ID de usuario es obligatorio")
    private Long userId;
    
    @Schema(description = "Monto monetario de la transacción (Debe ser mayor a cero)", example = "25.00", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a cero")
    private BigDecimal amount;
}