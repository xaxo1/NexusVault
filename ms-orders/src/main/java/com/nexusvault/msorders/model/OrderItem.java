package com.nexusvault.msorders.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long productId; // ID del producto del ms-catalog

    @NotNull
    @Positive
    private Integer quantity; // ¿Cuántos compró de este mismo?

    @NotNull
    private BigDecimal priceAtPurchase; // El precio en ese momento (por si cambia mañana)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order; // A qué boleta pertenece
}