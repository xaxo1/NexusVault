package com.nexusvault.msinventory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Objeto de transferencia de datos utilizado para reportar cambios (incrementos o decrementos)
 * en el inventario físico de los productos.
 */
@Schema(description = "Estructura de datos para reportar incrementos o decrementos en el stock físico")
@Data
public class StockAdjustmentDTO {

    @Schema(description = "Identificador único correlativo del producto proveniente del microservicio de catálogo", example = "1001")
    @NotNull(message = "El ID del producto es obligatorio")
    private Long productId;

    @Schema(description = "Número absoluto de unidades físicas que se van a alterar en el inventario", example = "15")
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad a modificar debe ser al menos 1")
    private Integer quantity;
}