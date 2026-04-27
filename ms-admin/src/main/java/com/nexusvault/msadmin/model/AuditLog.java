package com.nexusvault.msadmin.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs") // La tabla en MySQL para la bitácora
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del administrador es obligatorio")
    @Column(name = "admin_id", nullable = false)
    private Long adminId; // Quién hizo la acción (el ID del admin)

    @NotBlank(message = "La acción realizada es obligatoria")
    @Column(nullable = false)
    private String action; // Ej: "UPDATE_PRODUCT_PRICE", "BAN_USER", "DELETE_REVIEW"

    @NotBlank(message = "El ID de la entidad afectada es obligatorio")
    @Column(name = "target_entity_id", nullable = false)
    private String targetEntityId; // A quién o a qué le hizo el cambio (ID del producto, ID del usuario, etc.)

    @NotBlank(message = "Los detalles de la acción son obligatorios")
    @Column(length = 500, nullable = false)
    private String details; // Explicación del cambio: "Se cambió el precio de 100 a 50"

    @Column(updatable = false)
    private LocalDateTime timestamp; // Cuándo ocurrió

    // PRO-TIP: Registra la fecha y hora exactas del evento de seguridad automáticamente
    @PrePersist
    protected void onAudit() {
        this.timestamp = LocalDateTime.now();
    }
}