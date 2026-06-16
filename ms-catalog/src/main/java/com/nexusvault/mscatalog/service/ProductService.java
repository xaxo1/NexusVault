package com.nexusvault.mscatalog.service;

import com.nexusvault.mscatalog.model.ModelProduct;
import com.nexusvault.mscatalog.model.Rarity;
import com.nexusvault.mscatalog.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j 
@Service 
@RequiredArgsConstructor // Sustituye la inyección directa @Autowired por constructor automático
public class ProductService {

    private final ProductRepository productRepository;

    public List<ModelProduct> getAllProducts() {
        log.info("Obteniendo todos los productos de la base de datos");
        return productRepository.findAll();
    }

    public Optional<ModelProduct> getProductById(Long id) {
        log.info("Buscando producto con ID: {}", id);
        return productRepository.findById(id);
    }

    public ModelProduct saveProduct(ModelProduct product) {
        log.info("Guardando nuevo producto: {}", product.getName());
        if (product.isOnSale() && product.getOriginalPrice() == null) {
            product.setOriginalPrice(product.getPrice());
        }
        return productRepository.save(product);
    }

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

    public void deleteProduct(Long id) {
        log.info("Eliminando producto con ID: {}", id);
        productRepository.deleteById(id);
    }

    public List<ModelProduct> getProductsOnSale() {
        log.info("Buscando productos en oferta");
        return productRepository.findByIsOnSaleTrue();
    }

    public List<ModelProduct> getProductsByRarity(Rarity rarity) {
        log.info("Buscando productos con rareza: {}", rarity);
        return productRepository.findByRarity(rarity);
    }
}