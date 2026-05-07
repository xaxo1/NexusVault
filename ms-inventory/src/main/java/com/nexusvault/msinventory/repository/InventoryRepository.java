package com.nexusvault.msinventory.repository;

import com.nexusvault.msinventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    // Método VITAL: Buscar el inventario de un producto específico usando el ID del catálogo
    // Devuelve un Optional por si buscamos un producto que no existe en el inventario
    Optional<Inventory> findByProductId(Long productId);

    // Método extra: Útil para mostrar alertas de "Agotado" en el futuro
    // Busca todos los registros de inventario donde el stock sea 0
    List<Inventory> findByStock(Integer stock);
}