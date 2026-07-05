package com.nexusvault.msinventory.service.impl;

import com.nexusvault.msinventory.dto.StockAdjustmentDTO;
import com.nexusvault.msinventory.model.Inventory;
import com.nexusvault.msinventory.repository.InventoryRepository;
import com.nexusvault.msinventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación del servicio de gestión de inventarios.
 * Proporciona la lógica de negocio concreta para consultar, sumar y restar stock a los productos.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    /**
     * Recupera el registro de inventario de un producto mediante su identificador.
     *
     * @param productId El identificador del producto.
     * @return El registro de inventario actual del producto.
     * @throws IllegalArgumentException si el producto no se encuentra registrado.
     */
    @Override
    @Transactional(readOnly = true)
    public Inventory getStockByProductId(Long productId) {
        return inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new IllegalArgumentException("El producto con ID " + productId + " no está registrado en el inventario."));
    }

    /**
     * Incrementa el stock de un producto de acuerdo a los datos provistos.
     * Si el inventario para el producto no existe, lo inicializa.
     *
     * @param adjustment Detalles del ajuste, que incluyen el ID del producto y la cantidad a sumar.
     * @return El registro de inventario guardado con la cantidad actualizada.
     */
    @Override
    @Transactional
    public Inventory addStock(StockAdjustmentDTO adjustment) {
        log.info("Incrementando stock para el producto ID: {} en {} unidades", adjustment.getProductId(), adjustment.getQuantity());
        
        // Si el producto ya existe, se suma. Si no, se crea un registro nuevo.
        Inventory inventory = inventoryRepository.findByProductId(adjustment.getProductId())
                .orElseGet(() -> {
                    Inventory newInv = new Inventory();
                    newInv.setProductId(adjustment.getProductId());
                    newInv.setStock(0);
                    return newInv;
                });

        inventory.setStock(inventory.getStock() + adjustment.getQuantity());
        return inventoryRepository.save(inventory);
    }

    /**
     * Disminuye el stock de un producto basándose en los datos provistos.
     * Verifica que el inventario tenga existencias suficientes antes de reducir.
     *
     * @param adjustment Detalles del ajuste, que incluyen el ID del producto y la cantidad a restar.
     * @return El registro de inventario guardado con la cantidad actualizada.
     * @throws IllegalStateException si el stock actual no es suficiente para la reducción solicitada.
     */
    @Override
    @Transactional
    public Inventory reduceStock(StockAdjustmentDTO adjustment) {
        log.info("Reduciendo stock para el producto ID: {} en {} unidades", adjustment.getProductId(), adjustment.getQuantity());
        
        Inventory inventory = getStockByProductId(adjustment.getProductId());

        // Regla de negocio crítica: Validar existencias suficientes
        if (inventory.getStock() < adjustment.getQuantity()) {
            throw new IllegalStateException("Stock insuficiente para el producto ID: " + adjustment.getProductId() 
                    + ". Stock disponible: " + inventory.getStock());
        }

        inventory.setStock(inventory.getStock() - adjustment.getQuantity());
        return inventoryRepository.save(inventory);
    }

    /**
     * Consulta y devuelve la lista de productos cuyo stock es igual a cero.
     *
     * @return Lista de entidades de inventario sin existencias.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Inventory> getOutOfStockProducts() {
        log.warn("Consultando productos sin existencias en el sistema (Stock = 0)");
        return inventoryRepository.findByStock(0);
    }
}