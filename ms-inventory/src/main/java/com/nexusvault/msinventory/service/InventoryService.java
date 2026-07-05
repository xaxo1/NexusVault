package com.nexusvault.msinventory.service;

import com.nexusvault.msinventory.dto.StockAdjustmentDTO;
import com.nexusvault.msinventory.model.Inventory;
import java.util.List;

/**
 * Interfaz para el servicio de gestión de inventario.
 * Define las operaciones principales relacionadas con el control del stock de productos.
 */
public interface InventoryService {
    /**
     * Recupera el stock de un producto utilizando su identificador.
     *
     * @param productId Identificador del producto a consultar.
     * @return Registro de inventario del producto.
     */
    Inventory getStockByProductId(Long productId);

    /**
     * Incrementa la cantidad en stock de un producto.
     *
     * @param adjustment Detalles del ajuste de stock a incrementar.
     * @return Registro de inventario actualizado.
     */
    Inventory addStock(StockAdjustmentDTO adjustment);

    /**
     * Reduce la cantidad en stock de un producto, verificando que haya disponibilidad.
     *
     * @param adjustment Detalles del ajuste de stock a reducir.
     * @return Registro de inventario actualizado.
     */
    Inventory reduceStock(StockAdjustmentDTO adjustment);

    /**
     * Obtiene una lista de los productos que actualmente no tienen existencias.
     *
     * @return Lista de inventarios con stock igual a cero.
     */
    List<Inventory> getOutOfStockProducts();
}