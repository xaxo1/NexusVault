package com.nexusvault.msorders.repository;

import com.nexusvault.msorders.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio de acceso a datos para las líneas de detalle de las órdenes (OrderItem).
 * Proporciona métodos para consultar ítems asociados a productos o a órdenes.
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    
    /**
     * Consulta cuántas veces o en qué líneas de detalle de orden aparece un producto particular.
     *
     * @param productId El identificador del producto en el catálogo.
     * @return Lista de todos los ítems de órdenes donde se adquirió el producto.
     */
    // Método extra: Buscar en cuántas órdenes está presente un producto específico
    List<OrderItem> findByProductId(Long productId);
}