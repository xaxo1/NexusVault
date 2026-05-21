package com.nexusvault.mscatalog.controller;

import com.nexusvault.mscatalog.model.ModelProduct;
import com.nexusvault.mscatalog.model.Rarity;
import com.nexusvault.mscatalog.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Indica que esta clase devuelve datos (JSON) en lugar de vistas HTML
@RequestMapping("/api/products") // Define la URL base: http://localhost:8081/api/products
public class ProductController {

    @Autowired
    private ProductService productService;

    // 1. Obtener todos los productos
    @GetMapping
    public List<ModelProduct> getAll() {
        return productService.getAllProducts();
    }

    // 2. Obtener un producto por ID
    // Usamos ResponseEntity para devolver un 404 si el producto no existe
    @GetMapping("/{id}")
    public ResponseEntity<ModelProduct> getById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. Crear un nuevo producto
    // @Valid activa las validaciones que pusiste en tu modelo (@NotBlank, @DecimalMin, etc.)
    @PostMapping
    public ModelProduct create(@Valid @RequestBody ModelProduct product) {
        return productService.saveProduct(product);
    }

    // 4. Eliminar un producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // 5. Endpoint especial: Solo productos en oferta (Sale)
    // URL: http://localhost:8081/api/products/offers
    @GetMapping("/offers")
    public List<ModelProduct> getOffers() {
        return productService.getProductsOnSale();
    }

    // 6. Endpoint especial: Filtrar por rareza
    // URL: http://localhost:8081/api/products/rarity/LEGENDARY
    @GetMapping("/rarity/{rarity}")
    public List<ModelProduct> getByRarity(@PathVariable Rarity rarity) {
        return productService.getProductsByRarity(rarity);
    }
}