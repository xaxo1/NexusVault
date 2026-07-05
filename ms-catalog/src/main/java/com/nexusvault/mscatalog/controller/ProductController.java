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

/**
 * Controlador REST encargado de gestionar las operaciones sobre el catálogo de productos.
 * Expone los endpoints para realizar el CRUD y filtros personalizados.
 */
@Slf4j
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
//1-acá el tag general
@Tag(name = "Productos", description = "Endpoints CRUD y operaciones avanzadas para administrar el catálogo de ítems")
public class ProductController {

    private final ProductService productService;

    /**
     * Obtiene una lista de todos los productos del catálogo.
     *
     * @return una respuesta con la lista de productos y estado HTTP 200 (OK).
     */
    @GetMapping
    @Operation(summary = "Obtener todos los productos", description = "Recupera una lista completa con todos los ítems almacenados en la base de datos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos devuelta exitosamente")
    })
    public ResponseEntity<List<ModelProduct>> getAll() {
        log.info("Petición REST recibida: Obtener todos los productos");
        return ResponseEntity.ok(productService.getAllProducts());
    }

    /**
     * Obtiene los detalles de un producto utilizando su identificador único.
     *
     * @param id el identificador del producto.
     * @return una respuesta con el producto si existe, o estado HTTP 404 (NOT FOUND).
     */
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

    /**
     * Crea y registra un nuevo producto en el catálogo de la tienda.
     *
     * @param product los datos del producto a crear.
     * @return una respuesta con el producto creado y estado HTTP 201 (CREATED).
     */
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

    /**
     * Actualiza la información de un producto existente.
     *
     * @param id el identificador del producto a actualizar.
     * @param productData los nuevos datos del producto.
     * @return una respuesta con el producto actualizado, o estado HTTP 404 (NOT FOUND) si no existe.
     */
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

    /**
     * Elimina físicamente un producto del catálogo.
     *
     * @param id el identificador del producto a eliminar.
     * @return una respuesta con estado HTTP 204 (NO CONTENT).
     */
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

    /**
     * Obtiene una lista de todos los productos que actualmente se encuentran en oferta.
     *
     * @return una respuesta con la lista de productos en oferta y estado HTTP 200 (OK).
     */
    @GetMapping("/offers")
    @Operation(summary = "Obtener productos en oferta", description = "Filtra de manera automática todos los registros cuya propiedad de oferta (isOnSale) esté configurada en verdadero.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de ofertas recuperada con éxito")
    })
    public ResponseEntity<List<ModelProduct>> getOffers() {
        log.info("Petición REST recibida: Obtener productos en oferta");
        return ResponseEntity.ok(productService.getProductsOnSale());
    }

    /**
     * Filtra los productos del catálogo en base a su nivel de rareza.
     *
     * @param rarity el nivel de rareza por el cual filtrar (ej. COMMON, LEGENDARY).
     * @return una respuesta con la lista de productos filtrados y estado HTTP 200 (OK).
     */
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