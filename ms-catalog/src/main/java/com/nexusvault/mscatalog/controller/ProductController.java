package com.nexusvault.mscatalog.controller;

import com.nexusvault.mscatalog.model.ModelProduct;
import com.nexusvault.mscatalog.model.Rarity;
import com.nexusvault.mscatalog.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j // Log sencillo para cumplir la rúbrica
@RestController // Indica que esta clase devuelve datos (JSON) en lugar de vistas HTML
@RequestMapping("/api/products") // Define la URL base: http://localhost:8081/api/products
public class ProductController {

    @Autowired
    private ProductService productService;

    // 1. Obtener todos los productos (Devuelve ResponseEntity como pide la rúbrica)
    @GetMapping
    public ResponseEntity<List<ModelProduct>> getAll() {
        log.info("Petición REST recibida: Obtener todos los productos");
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // 2. Obtener un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<ModelProduct> getById(@PathVariable Long id) {
        log.info("Petición REST recibida: Obtener producto por ID: {}", id);
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. Crear un nuevo producto
    // @Valid activa las validaciones. Devuelve 201 CREATED (Mejor práctica REST)
    @PostMapping
    public ResponseEntity<ModelProduct> create(@Valid @RequestBody ModelProduct product) {
        log.info("Petición REST recibida: Crear nuevo producto");
        ModelProduct savedProduct = productService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    // 4. Actualizar un producto (NUEVO ENDPOINT PUT PARA COMPLETAR CRUD)
    @PutMapping("/{id}")
    public ResponseEntity<ModelProduct> update(@PathVariable Long id, @Valid @RequestBody ModelProduct productData) {
        log.info("Petición REST recibida: Actualizar producto con ID: {}", id);
        return productService.updateProduct(id, productData)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 5. Eliminar un producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Petición REST recibida: Eliminar producto con ID: {}", id);
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // 6. Endpoint especial: Solo productos en oferta (Sale)
    @GetMapping("/offers")
    public ResponseEntity<List<ModelProduct>> getOffers() {
        log.info("Petición REST recibida: Obtener productos en oferta");
        return ResponseEntity.ok(productService.getProductsOnSale());
    }

    // 7. Endpoint especial: Filtrar por rareza
    @GetMapping("/rarity/{rarity}")
    public ResponseEntity<List<ModelProduct>> getByRarity(@PathVariable Rarity rarity) {
        log.info("Petición REST recibida: Filtrar productos por rareza: {}", rarity);
        return ResponseEntity.ok(productService.getProductsByRarity(rarity));
    }
}