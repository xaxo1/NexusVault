package com.nexusvault.mscatalog.service;

import com.nexusvault.mscatalog.model.ModelProduct;
import com.nexusvault.mscatalog.model.Rarity;
import com.nexusvault.mscatalog.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Esta anotación le dice a Spring que esta clase contiene la lógica de negocio
public class ProductService {

    @Autowired // Inyectamos el Repository para poder comunicarnos con la base de datos
    private ProductRepository productRepository;

    // 1. Obtener todos los productos (útil para la tienda principal)
    public List<ModelProduct> getAllProducts() {
        return productRepository.findAll();
    }

    // 2. Obtener un producto por su ID
    // Usamos Optional porque el producto podría no existir en la BD
    public Optional<ModelProduct> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // 3. Guardar o Actualizar un producto
    // Aquí aplicamos lógica especial basada en tu modelo
    public ModelProduct saveProduct(ModelProduct product) {
        // Lógica de Negocio: Si el producto está en oferta (isOnSale) 
        // pero no tiene un precio original guardado, lo respaldamos.
        if (product.isOnSale() && product.getOriginalPrice() == null) {
            product.setOriginalPrice(product.getPrice());
        }
        return productRepository.save(product);
    }

    // 4. Eliminar un producto
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // 5. Filtrar solo los que están en oferta (Sale)
    public List<ModelProduct> getProductsOnSale() {
        return productRepository.findByIsOnSaleTrue();
    }

    // 6. Filtrar por Rareza
    public List<ModelProduct> getProductsByRarity(Rarity rarity) {
        return productRepository.findByRarity(rarity);
    }
}