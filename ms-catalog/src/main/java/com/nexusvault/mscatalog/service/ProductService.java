package com.nexusvault.mscatalog.service;

import com.nexusvault.mscatalog.model.ModelProduct;
import com.nexusvault.mscatalog.model.Rarity;
import com.nexusvault.mscatalog.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio encargado de gestionar la lógica de negocio para los productos del catálogo.
 * Proporciona operaciones para administrar y consultar el inventario.
 */
@Slf4j 
@Service 
@RequiredArgsConstructor // Sustituye la inyección directa @Autowired por constructor automático
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * Obtiene todos los productos registrados en la base de datos.
     *
     * @return una lista con todos los productos.
     */
    public List<ModelProduct> getAllProducts() {
        log.info("Obteniendo todos los productos de la base de datos");
        return productRepository.findAll();
    }

    /**
     * Busca un producto específico utilizando su identificador único.
     *
     * @param id el identificador del producto.
     * @return un {@link Optional} con el producto si existe, o vacío si no se encuentra.
     */
    public Optional<ModelProduct> getProductById(Long id) {
        log.info("Buscando producto con ID: {}", id);
        return productRepository.findById(id);
    }

    /**
     * Guarda un nuevo producto en el catálogo.
     * Si el producto está en oferta y no tiene precio original, asume el precio actual.
     *
     * @param product los datos del producto a registrar.
     * @return el producto persistido.
     */
    public ModelProduct saveProduct(ModelProduct product) {
        log.info("Guardando nuevo producto: {}", product.getName());
        if (product.isOnSale() && product.getOriginalPrice() == null) {
            product.setOriginalPrice(product.getPrice());
        }
        return productRepository.save(product);
    }

    /**
     * Actualiza los datos de un producto existente.
     *
     * @param id el identificador del producto a actualizar.
     * @param newProductData los nuevos datos del producto.
     * @return un {@link Optional} con el producto actualizado si existe.
     */
    public Optional<ModelProduct> updateProduct(Long id, ModelProduct newProductData) {
        log.info("Intentando actualizar producto con ID: {}", id);
        return productRepository.findById(id).map(existingProduct -> {
            existingProduct.setName(newProductData.getName());
            existingProduct.setDescription(newProductData.getDescription());
            existingProduct.setPrice(newProductData.getPrice());
            existingProduct.setOnSale(newProductData.isOnSale());
            existingProduct.setOriginalPrice(newProductData.getOriginalPrice());
            existingProduct.setRarity(newProductData.getRarity());
            existingProduct.setImageUrl(newProductData.getImageUrl());
            
            log.info("Producto actualizado exitosamente: {}", id);
            return productRepository.save(existingProduct);
        });
    }

    /**
     * Elimina físicamente un producto del sistema.
     *
     * @param id el identificador del producto a eliminar.
     */
    public void deleteProduct(Long id) {
        log.info("Eliminando producto con ID: {}", id);
        productRepository.deleteById(id);
    }

    /**
     * Obtiene una lista de todos los productos que están marcados en oferta.
     *
     * @return una lista de productos en oferta.
     */
    public List<ModelProduct> getProductsOnSale() {
        log.info("Buscando productos en oferta");
        return productRepository.findByIsOnSaleTrue();
    }

    /**
     * Filtra y obtiene los productos según su nivel de rareza.
     *
     * @param rarity el nivel de rareza utilizado como filtro.
     * @return una lista de productos que coinciden con la rareza especificada.
     */
    public List<ModelProduct> getProductsByRarity(Rarity rarity) {
        log.info("Buscando productos con rareza: {}", rarity);
        return productRepository.findByRarity(rarity);
    }
}