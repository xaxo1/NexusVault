package com.nexusvault.msorders.repository;

import com.nexusvault.msorders.enums.OrderStatus;
import com.nexusvault.msorders.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Método VITAL: Para mostrar el "Historial de Compras" de un usuario en el Frontend
    List<Order> findByUserId(Long userId);

    // Método extra: Para que el sistema busque las órdenes "PENDING" y las intente cobrar
    List<Order> findByStatus(OrderStatus status);
}