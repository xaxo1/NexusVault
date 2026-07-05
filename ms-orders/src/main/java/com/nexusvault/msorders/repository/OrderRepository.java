package com.nexusvault.msorders.repository;

import com.nexusvault.msorders.model.OrderStatus;
import com.nexusvault.msorders.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio de acceso a datos para gestionar la cabecera de las órdenes (Order).
 * Facilita las operaciones de persistencia y consultas específicas por estado o usuario.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Recupera todas las órdenes pertenecientes a un determinado usuario.
     *
     * @param userId Identificador único del usuario.
     * @return Lista de órdenes asociadas al usuario.
     */
    // Método VITAL: Para mostrar el "Historial de Compras" de un usuario en el Frontend
    List<Order> findByUserId(Long userId);

    /**
     * Busca y obtiene todas las órdenes que se encuentren en un estado particular.
     *
     * @param status El estado de la orden (por ejemplo, PENDING o PAID).
     * @return Lista de órdenes con el estado indicado.
     */
    // Método extra: Para que el sistema busque las órdenes "PENDING" y las intente cobrar
    List<Order> findByStatus(OrderStatus status);
}