package com.nexusvault.mscatalog.controller;

import com.nexusvault.mscatalog.model.ModelProduct;
import com.nexusvault.mscatalog.model.Rarity;
import com.nexusvault.mscatalog.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
//1-acá el tag general
@Tag(name = "Productos", description = "Endpoints CRUD y operaciones avanzadas para administrar el catálogo de ítems")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "Obtener todos los productos", description = "Recupera una lista completa con todos los ítems almacenados en la base de datos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos devuelta exitosamente")
    })
    public ResponseEntity<List<ModelProduct>> getAll() {
        log.info("Petición REST recibida: Obtener todos los productos");
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Busca un producto específico utilizando su identificador único numérico.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado correctamente"),
        @ApiResponse(responseCode = "404", description = "El producto con el ID especificado no existe", content = @Content)
    })
    public ResponseEntity<ModelProduct> getById(@PathVariable Long id) {
        log.info("Petición REST recibida: Obtener producto por ID: {}", id);
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo producto", description = "Registra un producto en el catálogo verificando que las restricciones del esquema sean válidas.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto creado de forma exitosa"),
        @ApiResponse(responseCode = "400", description = "Cuerpo de la petición inválido o campos faltantes", content = @Content)
    })
    public ResponseEntity<ModelProduct> create(@Valid @RequestBody ModelProduct product) {
        log.info("Petición REST recibida: Crear nuevo producto");
        ModelProduct savedProduct = productService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto existente", description = "Modifica los campos de un producto basándose en su ID. Revalida las restricciones impuestas.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado de forma exitosa"),
        @ApiResponse(responseCode = "404", description = "No se encontró el producto a modificar", content = @Content),
        @ApiResponse(responseCode = "400", description = "Datos de actualización inválidos", content = @Content)
    })
    public ResponseEntity<ModelProduct> update(@PathVariable Long id, @Valid @RequestBody ModelProduct productData) {
        log.info("Petición REST recibida: Actualizar producto con ID: {}", id);
        return productService.updateProduct(id, productData)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto", description = "Remueve permanentemente un registro del catálogo de la tienda utilizando su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "24", description = "Producto eliminado correctamente (No Content)"),
        @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Petición REST recibida: Eliminar producto con ID: {}", id);
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/offers")
    @Operation(summary = "Obtener productos en oferta", description = "Filtra de manera automática todos los registros cuya propiedad de oferta (isOnSale) esté configurada en verdadero.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de ofertas recuperada con éxito")
    })
    public ResponseEntity<List<ModelProduct>> getOffers() {
        log.info("Petición REST recibida: Obtener productos en oferta");
        return ResponseEntity.ok(productService.getProductsOnSale());
    }

    @GetMapping("/rarity/{rarity}")
    @Operation(summary = "Filtrar productos por rareza", description = "Recupera los productos segmentados bajo un tag específico del enumerado de rarezas.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Productos filtrados correctamente"),
        @ApiResponse(responseCode = "400", description = "El tipo de rareza provisto no es válido", content = @Content)
    })
    public ResponseEntity<List<ModelProduct>> getByRarity(@PathVariable Rarity rarity) {
        log.info("Petición REST recibida: Filtrar productos por rareza: {}", rarity);
        return ResponseEntity.ok(productService.getProductsByRarity(rarity));
    }
}