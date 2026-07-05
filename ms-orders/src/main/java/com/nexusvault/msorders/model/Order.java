package com.nexusvault.msorders.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//-------1-acá comienza con el schema general del modelo
/**
 * Entidad de dominio que representa una orden de compra transaccional generada por un usuario.
 * Centraliza el historial, importe y estado de la operación.
 */
@Schema(description = "Entidad principal que representa una cabecera de orden transaccional")
@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {

    //------2-acá comienza con el id
    @Schema(description = "Identificador único incremental de la orden", example = "4001")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "ID único del cliente que ejecutó la compra", example = "12")
    @NotNull(message = "El ID del usuario es obligatorio")
    private Long userId;

    @Schema(description = "Lista relacional de los productos agregados a esta cabecera")
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    @Schema(description = "Importe total acumulado neto de la transacción física", example = "250.50")
    @NotNull
    private BigDecimal totalAmount;

    @Schema(description = "Estado actual del ciclo de vida del pedido", example = "PENDING")
    @Enumerated(EnumType.STRING)
    private OrderStatus status; 

    @Schema(description = "Timestamp de creación del registro en el sistema", example = "2026-03-31T19:40:00")
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /**
     * Metodo invocado antes de persistir la entidad. 
     * Asigna automáticamente la fecha de creación y el estado pendiente por defecto.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = OrderStatus.PENDING;
        }
    }
    
    /**
     * Añade un nuevo ítem a la lista de esta orden.
     * Mantiene la consistencia bidireccional estableciendo la orden en el ítem provisto.
     *
     * @param item El ítem a agregar a la orden.
     */
    public void addOrderItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }
}