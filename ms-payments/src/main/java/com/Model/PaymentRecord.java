package com.nexusvault.mspayments.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_records") // La tabla en MySQL
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID de la orden es obligatorio")
    @Column(name = "order_id", unique = true, nullable = false)
    private Long orderId; // Se vincula con el ms-orders

    @NotNull(message = "El monto pagado es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a cero")
    @Column(name = "amount_paid", nullable = false)
    private BigDecimal amountPaid; // Seguimos usando BigDecimal para el dinero

    @NotBlank(message = "El método de pago es obligatorio")
    @Column(name = "payment_method", nullable = false)
    private String paymentMethod; // Ej: "CREDIT_CARD", "PAYPAL", "WALLET_NXP"

    @NotBlank(message = "El estado del pago es requerido")
    @Column(nullable = false)
    private String status; // Ej: "SUCCESS", "FAILED", "PROCESSING"

    @Column(name = "external_transaction_id")
    private String externalTransactionId; // El código de recibo que te da el banco o la pasarela

    @Column(name = "processed_at", updatable = false)
    private LocalDateTime processedAt;

    // PRO-TIP: Registra la fecha exacta en la que se intentó cobrar
    @PrePersist
    protected void onProcess() {
        this.processedAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = "PROCESSING"; // Si no enviamos estado, empieza procesando
        }
    }
}