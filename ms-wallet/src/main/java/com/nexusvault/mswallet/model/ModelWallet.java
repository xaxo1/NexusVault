package com.nexusvault.mswallet.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

//-------1-acá comienza con el schema general del modelo
@Schema(description = "Entidad que representa la billetera digital y balance monetario de precisión de un usuario")
@Entity
@Table(name = "wallets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModelWallet {

    //------2-acá comienza con el id
    @Schema(description = "ID único e incremental interno de la billetera en la base de datos", example = "7001")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "ID del usuario propietario del balance (Relación lógica distribuida)", example = "3001")
    @Column(name = "user_id", unique = true, nullable = false)
    private Long userId;

    @Schema(description = "Saldo actual con precisión garantizada de dos decimales para transacciones", example = "450.50")
    @Column(name = "saldo_actual", nullable = false, precision = 10, scale = 2)
    private BigDecimal saldoActual;

    @Schema(description = "Identificador de la cuenta de banco o pasarela vinculada para recargas", example = "ES2114900002341234")
    @Column(name = "cuenta_bancaria_vinculada", length = 100)
    private String cuentaBancariaVinculada;

    @Schema(description = "Timestamp inmutable de la habilitación de la billetera", example = "2026-01-10T12:00:00")
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp dinámico de la última actualización de saldo o cobro", example = "2026-06-16T18:05:00")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Schema(description = "Flag de control operativo de la cuenta de cobro", example = "true")
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.saldoActual == null) {
            this.saldoActual = BigDecimal.ZERO;
        }
        if (this.isActive == null) {
            this.isActive = true;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}