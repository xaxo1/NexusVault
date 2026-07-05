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

/**
 * Servicio central responsable de manejar la lógica de negocio de las órdenes.
 * Coordina la creación, cálculo de totales, consultas y actualizaciones de estados.
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    /**
     * Genera una nueva orden para el usuario y calcula el monto total en base a sus ítems.
     *
     * @param userId Identificador del usuario que realiza la compra.
     * @param items Lista de artículos a incluir en la orden.
     * @return La orden creada con sus detalles e importe calculados.
     */
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

    /**
     * Obtiene el historial completo de órdenes de un usuario determinado.
     *
     * @param userId Identificador del usuario.
     * @return Lista de órdenes pertenecientes al usuario.
     */
    @Transactional(readOnly = true)
    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    /**
     * Busca una orden específica por su identificador único.
     *
     * @param orderId Identificador numérico de la orden.
     * @return La orden con el ID proporcionado.
     * @throws RuntimeException si la orden no es encontrada.
     */
    @Transactional(readOnly = true)
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + orderId));
    }

    /**
     * Modifica y actualiza el estado de una orden.
     *
     * @param orderId Identificador de la orden a actualizar.
     * @param newStatus Nuevo estado que será aplicado.
     * @return La orden ya actualizada.
     */
    @Transactional
    public Order updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = getOrderById(orderId);
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }
}