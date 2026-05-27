package com.nexusvault.mscatalog.service;

import com.nexusvault.mscatalog.model.ModelProduct;
import com.nexusvault.mscatalog.model.Rarity;
import com.nexusvault.mscatalog.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j // Agregamos Slf4j para tener logs simples y cumplir con la rúbrica
@Service // Esta anotación le dice a Spring que esta clase contiene la lógica de negocio
public class ProductService {

    @Autowired // Inyectamos el Repository para poder comunicarnos con la base de datos
    private ProductRepository productRepository;

    // 1. Obtener todos los productos (útil para la tienda principal)
    public List<ModelProduct> getAllProducts() {
        log.info("Obteniendo todos los productos de la base de datos");
        return productRepository.findAll();
    }

    // 2. Obtener un producto por su ID
    // Usamos Optional porque el producto podría no existir en la BD
    public Optional<ModelProduct> getProductById(Long id) {
        log.info("Buscando producto con ID: {}", id);
        return productRepository.findById(id);
    }

    // 3. Guardar un producto nuevo
    // Aquí aplicamos lógica especial basada en tu modelo
    public ModelProduct saveProduct(ModelProduct product) {
        log.info("Guardando nuevo producto: {}", product.getName());
        // Lógica de Negocio: Si el producto está en oferta (isOnSale) 
        // pero no tiene un precio original guardado, lo respaldamos.
        if (product.isOnSale() && product.getOriginalPrice() == null) {
            product.setOriginalPrice(product.getPrice());
        }
        return productRepository.save(product);
    }

    // 4. Actualizar un producto existente (NUEVO MÉTODO PARA COMPLETAR CRUD)
    public Optional<ModelProduct> updateProduct(Long id, ModelProduct newProductData) {
        log.info("Intentando actualizar producto con ID: {}", id);
        return productRepository.findById(id).map(existingProduct -> {
            existingProduct.setName(newProductData.getName());
            existingProduct.setDescription(newProductData.getDescription());
            existingProduct.setPrice(newProductData.getPrice());
            existingProduct.setOnSale(newProductData.isOnSale());
            existingProduct.setOriginalPrice(newProductData.getOriginalPrice());
            existingProduct.setRarity(newProductData.getRarity());
            
            log.info("Producto actualizado exitosamente: {}", id);
            return productRepository.save(existingProduct);
        });
    }

    // 5. Eliminar un producto
    public void deleteProduct(Long id) {
        log.info("Eliminando producto con ID: {}", id);
        productRepository.deleteById(id);
    }

    // 6. Filtrar solo los que están en oferta (Sale)
    public List<ModelProduct> getProductsOnSale() {
        log.info("Buscando productos en oferta");
        return productRepository.findByIsOnSaleTrue();
    }

    // 7. Filtrar por Rareza
    public List<ModelProduct> getProductsByRarity(Rarity rarity) {
        log.info("Buscando productos con rareza: {}", rarity);
        return productRepository.findByRarity(rarity);
    }
}