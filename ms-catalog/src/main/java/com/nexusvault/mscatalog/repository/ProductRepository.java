package com.nexusvault.mscatalog.repository;

import com.nexusvault.mscatalog.enums.Rarity;
import com.nexusvault.mscatalog.model.ModelProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ModelProduct, Long> {

    // Método extra: Para mostrar en el Frontend solo los productos que están en oferta
    List<ModelProduct> findByIsOnSaleTrue();

    // Método extra: Para que los usuarios puedan filtrar por rareza (Ej: buscar todos los "LEGENDARY")
    List<ModelProduct> findByRarity(Rarity rarity);
    
}