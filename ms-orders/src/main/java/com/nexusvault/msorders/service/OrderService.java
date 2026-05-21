package com.nexusvault.msorders.service;

import com.nexusvault.msorders.model.Order;
import com.nexusvault.msorders.model.OrderItem;
import com.nexusvault.msorders.model.OrderStatus;
import com.nexusvault.msorders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public Order createOrder(Long userId, List<OrderItem> items) {
        Order order = new Order();
        order.setUserId(userId);
        
        BigDecimal total = BigDecimal.ZERO;

        // Añadimos los items usando tu método de ayuda y calculamos el total real
        for (OrderItem item : items) {
            order.addOrderItem(item);
            
            // Calculamos: precio * cantidad
            BigDecimal itemTotal = item.getPriceAtPurchase().multiply(new BigDecimal(item.getQuantity()));
            total = total.add(itemTotal);
        }

        order.setTotalAmount(total);

        // El status y el createdAt se setearán solos gracias a tu @PrePersist
        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + orderId));
    }

    @Transactional
    public Order updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = getOrderById(orderId);
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }
}