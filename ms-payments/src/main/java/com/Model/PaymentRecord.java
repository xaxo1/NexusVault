package com.nexusvault.mspayments.model;

import com.nexusvault.mspayments.enums.PaymentMethod;
import com.nexusvault.mspayments.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID de la orden es obligatorio")
    @Column(name = "order_id", unique = true, nullable = false)
    private Long orderId;

    @NotNull(message = "El monto pagado es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a cero")
    @Column(name = "amount_paid", nullable = false)
    private BigDecimal amountPaid;

    @NotNull(message = "El método de pago es obligatorio")
    @Enumerated(EnumType.STRING) // Guarda el nombre del enum en la BD
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod; 

    @NotNull(message = "El estado del pago es requerido")
    @Enumerated(EnumType.STRING) // Guarda el nombre del enum en la BD
    @Column(nullable = false)
    private PaymentStatus status; 

    @Column(name = "external_transaction_id")
    private String externalTransactionId;

    @Column(name = "processed_at", updatable = false)
    private LocalDateTime processedAt;

    @PrePersist
    protected void onProcess() {
        this.processedAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = PaymentStatus.PROCESSING; // Usamos el Enum aquí
        }
    }
}