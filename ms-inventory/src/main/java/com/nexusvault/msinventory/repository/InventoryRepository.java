package com.nexusvault.msinventory.repository;

import com.nexusvault.msinventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de acceso a datos para la entidad de Inventario.
 * Proporciona métodos para interactuar con la base de datos de control de existencias.
 */
@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    /**
     * Busca la información de inventario asociada al identificador de un producto específico.
     *
     * @param productId Identificador único del producto en el catálogo.
     * @return Un objeto Optional que contiene el registro de inventario si se encuentra, o vacío en caso contrario.
     */
    // Método VITAL: Buscar el inventario de un producto específico usando el ID del catálogo
    // Devuelve un Optional por si buscamos un producto que no existe en el inventario
    Optional<Inventory> findByProductId(Long productId);

    /**
     * Recupera una lista de inventarios basados en una cantidad exacta de stock disponible.
     * Útil para consultar productos que están sin existencias.
     *
     * @param stock Cantidad de stock a buscar.
     * @return Lista de registros de inventario con el stock indicado.
     */
    // Método extra: Útil para mostrar alertas de "Agotado" en el futuro
    // Busca todos los registros de inventario donde el stock sea 0
    List<Inventory> findByStock(Integer stock);
}