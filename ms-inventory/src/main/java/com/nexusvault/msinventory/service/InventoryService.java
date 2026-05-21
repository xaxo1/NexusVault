package com.nexusvault.msinventory.service;

import com.nexusvault.msinventory.dto.StockAdjustmentDTO;
import com.nexusvault.msinventory.model.Inventory;
import java.util.List;

public interface InventoryService {
    Inventory getStockByProductId(Long productId);
    Inventory addStock(StockAdjustmentDTO adjustment);
    Inventory reduceStock(StockAdjustmentDTO adjustment);
    List<Inventory> getOutOfStockProducts();
}