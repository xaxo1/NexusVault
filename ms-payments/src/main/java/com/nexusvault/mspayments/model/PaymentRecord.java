package com.nexusvault.mspayments.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

//-------1-acá comienza con el schema general del modelo
/**
 * Entidad de dominio que representa y audita una liquidación o transacción en el sistema de pagos.
 * Centraliza los datos del medio utilizado, importe, orden original y el código de confirmación del proveedor.
 */
@Schema(description = "Entidad representativa de la auditoría y estado físico del pago de una transacción")
@Entity
@Table(name = "payment_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRecord {

    //------2-acá comienza con el id
    @Schema(description = "ID único e incremental del recibo de pago local", example = "8005")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Identificador único de la orden asociada (Relación 1:1 estricta)", example = "4001")
    @NotNull(message = "El ID de la orden es obligatorio")
    @Column(name = "order_id", unique = true, nullable = false)
    private Long orderId;

    @Schema(description = "Importe total neto cobrado por la pasarela", example = "49.99")
    @NotNull(message = "El monto pagado es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a cero")
    @Column(name = "amount_paid", nullable = false)
    private BigDecimal amountPaid;

    @Schema(description = "Medio financiero procesado en la pasarela", example = "NXP_WALLET")
    @NotNull(message = "El método de pago es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod; 

    @Schema(description = "Estado actual del proceso de cobro", example = "SUCCESS")
    @NotNull(message = "El estado del pago es requerido")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status; 

    @Schema(description = "Código de transacción devuelto por el procesador bancario o pasarela externa", example = "NEXUS-TXN-A3B2C1")
    @Column(name = "external_transaction_id")
    private String externalTransactionId;

    @Schema(description = "Timestamp exacto en que concluyó la operación", example = "2026-03-31T21:10:15")
    @Column(name = "processed_at", updatable = false)
    private LocalDateTime processedAt;

    /**
     * Método invocado automáticamente antes de la persistencia de la entidad.
     * Define la marca de tiempo de la transacción y asigna un estado PROCESSING por defecto en caso de ausencia.
     */
    @PrePersist
    protected void onProcess() {
        this.processedAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = PaymentStatus.PROCESSING;
        }
    }
}