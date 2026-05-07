package com.nexusvault.msorders.repository;

import com.nexusvault.msorders.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    
    // Método extra: Buscar en cuántas órdenes está presente un producto específico
    List<OrderItem> findByProductId(Long productId);
}