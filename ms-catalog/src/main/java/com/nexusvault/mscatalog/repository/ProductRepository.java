package com.nexusvault.mscatalog.repository;

import com.nexusvault.mscatalog.model.ModelProduct;
import com.nexusvault.mscatalog.model.Rarity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la entidad {@link ModelProduct}.
 * Gestiona el acceso a datos y permite realizar consultas específicas sobre los productos.
 */
@Repository
public interface ProductRepository extends JpaRepository<ModelProduct, Long> {

    /**
     * Busca todos los productos que actualmente están marcados como en oferta.
     *
     * @return una lista de productos en oferta.
     */
    // 1. Para filtrar todos los objetos que están en oferta (Sale)
    // Spring Boot lo traduce a: SELECT * FROM products WHERE is_on_sale = true
    List<ModelProduct> findByIsOnSaleTrue();

    /**
     * Busca todos los productos que coinciden con una rareza específica.
     *
     * @param rarity el nivel de rareza buscado.
     * @return una lista de productos de la rareza indicada.
     */
    // 2. Para filtrar por rareza (Útil para que el usuario busque solo "LEGENDARY")
    List<ModelProduct> findByRarity(Rarity rarity);

    /**
     * Busca productos cuyo nombre contenga la cadena proporcionada, ignorando mayúsculas y minúsculas.
     *
     * @param name el texto a buscar en el nombre de los productos.
     * @return una lista de productos que coinciden con el criterio de búsqueda.
     */
    // 3. Para buscar productos por nombre (sin importar mayúsculas/minúsculas)
    // Útil para un buscador en el Frontend
    List<ModelProduct> findByNameContainingIgnoreCase(String name);
}