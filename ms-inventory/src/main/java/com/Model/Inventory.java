package com.nexusvault.msinventory.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory") // La tabla se llamará "inventory" en la base de datos
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del producto es obligatorio")
    @Column(name = "product_id", unique = true, nullable = false)
    private Long productId;

    @NotNull(message = "La cantidad en stock es obligatoria")
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(nullable = false)
    private Integer stock;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    // PRO-TIP: Esto actualiza la fecha automáticamente cada vez que se guarda o se modifica el stock
    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        this.lastUpdated = LocalDateTime.now();
    }
}