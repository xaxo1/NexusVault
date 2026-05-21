package com.nexusvault.mscatalog.repository;

import com.nexusvault.mscatalog.model.ModelProduct;
import com.nexusvault.mscatalog.model.Rarity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ModelProduct, Long> {

    // 1. Para filtrar todos los objetos que están en oferta (Sale)
    // Spring Boot lo traduce a: SELECT * FROM products WHERE is_on_sale = true
    List<ModelProduct> findByIsOnSaleTrue();

    // 2. Para filtrar por rareza (Útil para que el usuario busque solo "LEGENDARY")
    List<ModelProduct> findByRarity(Rarity rarity);

    // 3. Para buscar productos por nombre (sin importar mayúsculas/minúsculas)
    // Útil para un buscador en el Frontend
    List<ModelProduct> findByNameContainingIgnoreCase(String name);
}