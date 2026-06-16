package com.nexusvault.msinventory.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//-------1-acá comienza con el schema general del modelo
@Schema(description = "Entidad que representa el control físico y el balance de unidades almacenadas por cada producto")
@Entity
@Table(name = "inventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

    //------2-acá comienza con el id
    @Schema(description = "Llave primaria interna incremental del registro de inventario", example = "45")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "ID único de referencia cruzada con el microservicio de catálogo", example = "1001")
    @NotNull(message = "El ID del producto es obligatorio")
    @Column(name = "product_id", unique = true, nullable = false)
    private Long productId;

    @Schema(description = "Cantidad actual de existencias físicas disponibles en los almacenes", example = "120")
    @NotNull(message = "La cantidad en stock es obligatoria")
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(nullable = false)
    private Integer stock;

    @Schema(description = "Timestamp exacto del último movimiento físico o actualización en el registro", example = "2026-03-31T17:45:00")
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        this.lastUpdated = LocalDateTime.now();
    }
}