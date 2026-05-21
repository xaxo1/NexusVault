package com.nexusvault.msinventory.controller;

import com.nexusvault.msinventory.dto.StockAdjustmentDTO;
import com.nexusvault.msinventory.model.Inventory;
import com.nexusvault.msinventory.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/product/{productId}")
    public ResponseEntity<Inventory> getStock(@PathVariable Long productId) {
        return ResponseEntity.ok(inventoryService.getStockByProductId(productId));
    }

    @PostMapping("/add")
    public ResponseEntity<Inventory> addStock(@Valid @RequestBody StockAdjustmentDTO adjustmentDTO) {
        return ResponseEntity.ok(inventoryService.addStock(adjustmentDTO));
    }

    @PostMapping("/reduce")
    public ResponseEntity<Inventory> reduceStock(@Valid @RequestBody StockAdjustmentDTO adjustmentDTO) {
        return ResponseEntity.ok(inventoryService.reduceStock(adjustmentDTO));
    }

    @GetMapping("/out-of-stock")
    public ResponseEntity<List<Inventory>> getOutOfStock() {
        return ResponseEntity.ok(inventoryService.getOutOfStockProducts());
    }
}