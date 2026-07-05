package com.nexusvault.msorders.dto;

import com.nexusvault.msorders.model.OrderItem;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

/**
 * Objeto de transferencia de datos empleado para solicitar la creación de una nueva orden.
 * Agrupa el identificador del usuario y los artículos que desea adquirir.
 */
@Schema(description = "Estructura de entrada para gatillar la creación de una orden desde el carro de compras")
@Data
public class OrderCreateRequestDTO {

    @Schema(description = "ID del usuario comprador dentro del sistema", example = "12")
    @NotNull(message = "El ID del usuario es requerido")
    private Long userId;

    @Schema(description = "Colección detallada de ítems o skins seleccionadas con sus respectivas cantidades")
    @NotNull(message = "La orden debe contener items")
    private List<OrderItem> items;
}