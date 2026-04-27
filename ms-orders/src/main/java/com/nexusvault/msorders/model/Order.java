package com.nexusvault.msorders.model;

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
@Table(name = "orders") // El nombre de la tabla en MySQL
@Data
@NoArgsConstructor      // Constructor vacío obligatorio para JPA
@AllArgsConstructor     // Constructor con todos los argumentos
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del usuario comprador es obligatorio")
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull(message = "El ID de la skin a comprar es obligatorio")
    @Column(name = "skin_id", nullable = false)
    private Long productId;

    @NotNull(message = "El monto total es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto total debe ser mayor a cero")
    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @NotBlank(message = "El estado de la orden no puede estar vacío")
    @Column(nullable = false)
    private String status; // Ej: "PENDING", "COMPLETED", "FAILED"

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // PRO-TIP: Este método se ejecuta automáticamente justo ANTES de guardar en la BD
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now(); // Guarda la fecha y hora exacta
        if (this.status == null) {
            this.status = "PENDING"; // Si no le mandamos estado, por defecto será PENDIENTE
        }
    }
}