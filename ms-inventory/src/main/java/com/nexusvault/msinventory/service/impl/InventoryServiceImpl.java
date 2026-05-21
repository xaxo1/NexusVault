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

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    @Transactional(readOnly = true)
    public Inventory getStockByProductId(Long productId) {
        return inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new IllegalArgumentException("El producto con ID " + productId + " no está registrado en el inventario."));
    }

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

    @Override
    @Transactional(readOnly = true)
    public List<Inventory> getOutOfStockProducts() {
        log.warn("Consultando productos sin existencias en el sistema (Stock = 0)");
        return inventoryRepository.findByStock(0);
    }
}