package com.nexusvault.msorders.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Schema(description = "Detalle del ítem individual que compone el desglose de una orden")
@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
public class OrderItem {

    @Schema(description = "ID interno secuencial del renglón de la orden", example = "15002")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Identificador de referencia cruzada hacia el artículo en el catálogo", example = "101")
    @NotNull
    private Long productId;

    @Schema(description = "Número de unidades de la misma referencia añadidas por el usuario", example = "2")
    @NotNull
    @Positive
    private Integer quantity;

    @Schema(description = "Precio unitario histórico congelado al momento del cierre de la compra", example = "125.25")
    @NotNull
    private BigDecimal priceAtPurchase;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
}