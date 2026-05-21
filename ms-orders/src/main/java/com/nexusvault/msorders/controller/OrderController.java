package com.nexusvault.msorders.controller;

import com.nexusvault.msorders.model.Order;
import com.nexusvault.msorders.model.OrderItem;
import com.nexusvault.msorders.model.OrderStatus;
import com.nexusvault.msorders.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // POST: /api/v1/orders
    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderCreateRequest request) {
        Order newOrder = orderService.createOrder(request.getUserId(), request.getItems());
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }

    // GET: /api/v1/orders/user/{userId}
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrdersByUser(userId);
        return ResponseEntity.ok(orders);
    }

    // GET: /api/v1/orders/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        try {
            Order order = orderService.getOrderById(id);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // PATCH: /api/v1/orders/{id}/status?newStatus=PAID
    @PatchMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestParam OrderStatus newStatus) {
        try {
            Order updatedOrder = orderService.updateOrderStatus(id, newStatus);
            return ResponseEntity.ok(updatedOrder);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // --- DTO para recibir la petición de creación desde el Frontend ---
    @Data
    public static class OrderCreateRequest {
        @NotNull(message = "El ID del usuario es requerido")
        private Long userId;
        
        @NotNull(message = "La orden debe contener items")
        private List<OrderItem> items;
    }
}