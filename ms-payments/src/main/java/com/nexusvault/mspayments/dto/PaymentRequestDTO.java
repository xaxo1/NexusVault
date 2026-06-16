package com.nexusvault.mspayments.dto;

import com.nexusvault.mspayments.model.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Schema(description = "Payload necesario para procesar una transacción de pago contra una orden del sistema")
@Data
public class PaymentRequestDTO {

    @Schema(description = "ID único de la orden que se desea liquidar", example = "4001")
    @NotNull(message = "El ID de la orden es obligatorio")
    private Long orderId;

    @Schema(description = "Monto monetario de la operación (Debe ser superior a cero)", example = "49.99")
    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a cero")
    private BigDecimal amount;

    @Schema(description = "Método o canal de pago utilizado para la transferencia de fondos", example = "CREDIT_CARD")
    @NotNull(message = "El método de pago es obligatorio")
    private PaymentMethod paymentMethod;
}