package com.nexusvault.msinventory.controller;

import com.nexusvault.msinventory.dto.StockAdjustmentDTO;
import com.nexusvault.msinventory.model.Inventory;
import com.nexusvault.msinventory.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
//1-acá el tag general
@Tag(name = "Inventario", description = "Endpoints para la consulta, adición, deducción de unidades y reportes de stock en tiempo real")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/product/{productId}")
    @Operation(summary = "Consultar stock por ID de producto", description = "Obtiene las existencias físicas registradas de un producto en base a su identificador de catálogo.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Stock del producto consultado exitosamente"),
        @ApiResponse(responseCode = "404", description = "El producto no posee registros en la tabla de inventario", content = @Content)
    })
    public ResponseEntity<Inventory> getStock(@PathVariable Long productId) {
        return ResponseEntity.ok(inventoryService.getStockByProductId(productId));
    }

    @PostMapping("/add")
    @Operation(summary = "Incrementar existencias de stock", description = "Suma unidades al inventario de un producto. Si el producto no existía en stock, inicializa su registro automáticamente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Stock incrementado y actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o cantidades menores a 1", content = @Content)
    })
    public ResponseEntity<Inventory> addStock(@Valid @RequestBody StockAdjustmentDTO adjustmentDTO) {
        return ResponseEntity.ok(inventoryService.addStock(adjustmentDTO));
    }

    @PostMapping("/reduce")
    @Operation(summary = "Reducir existencias de stock", description = "Resta unidades al stock de un producto debido a ventas u operaciones de salida. Valida que haya stock suficiente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Stock disminuido correctamente"),
        @ApiResponse(responseCode = "400", description = "Cuerpo de la petición inválido", content = @Content),
        @ApiResponse(responseCode = "409", description = "Operación rechazada por stock insuficiente en el sistema", content = @Content)
    })
    public ResponseEntity<Inventory> reduceStock(@Valid @RequestBody StockAdjustmentDTO adjustmentDTO) {
        return ResponseEntity.ok(inventoryService.reduceStock(adjustmentDTO));
    }

    @GetMapping("/out-of-stock")
    @Operation(summary = "Listar productos sin existencias (Stock = 0)", description = "Retorna un listado consolidado con todos los registros de inventario que se han quedado desabastecidos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado de alertas por desabastecimiento obtenido con éxito")
    })
    public ResponseEntity<List<Inventory>> getOutOfStock() {
        return ResponseEntity.ok(inventoryService.getOutOfStockProducts());
    }
}