package com.nexusvault.msorders.controller;

import com.nexusvault.msorders.dto.OrderCreateRequestDTO;
import com.nexusvault.msorders.model.Order;
import com.nexusvault.msorders.model.OrderStatus;
import com.nexusvault.msorders.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
//1-acá el tag general de la rúbrica
@Tag(name = "Órdenes", description = "Endpoints para la emisión, consulta histórica de compras y transiciones de estados de órdenes")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Emitir una nueva orden de compra", description = "Recibe los ítems y el ID de usuario, realiza el cálculo automático del importe total y consolida la orden con estado inicial PENDING.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Orden de compra registrada y encolada con éxito"),
        @ApiResponse(responseCode = "400", description = "Payload inválido o faltan campos requeridos en los ítems", content = @Content)
    })
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderCreateRequestDTO request) {
        log.info("Petición REST recibida: Crear nueva orden para usuario ID: {}", request.getUserId());
        Order newOrder = orderService.createOrder(request.getUserId(), request.getItems());
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Obtener historial de órdenes de un usuario", description = "Recupera todas las órdenes asociadas a un identificador único de usuario para renderizar en su panel transaccional.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Historial transaccional recuperado de forma exitosa")
    })
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getOrdersByUser(userId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar orden por su ID", description = "Busca los detalles de un comprobante de orden específico por su identificador numérico incremental.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Detalles de la orden obtenidos correctamente"),
        @ApiResponse(responseCode = "404", description = "No existe ninguna orden con el ID proporcionado", content = @Content)
    })
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Actualizar estado de la orden", description = "Permite transicionar el estado actual de la orden (PENDING, PAID, SHIPPED, CANCELLED) mediante parámetros de consulta.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Transición de estado aplicada de forma correcta"),
        @ApiResponse(responseCode = "404", description = "Orden de compra no encontrada en la base de datos", content = @Content)
    })
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestParam OrderStatus newStatus) {
        Order updatedOrder = orderService.updateOrderStatus(id, newStatus);
        return ResponseEntity.ok(updatedOrder);
    }
}