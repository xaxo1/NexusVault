package com.nexusvault.mscatalog.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "products") // Ahora la tabla en MySQL se llamará "products"
@Data
@NoArgsConstructor      
@AllArgsConstructor     
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Column(unique = true, nullable = false)
    private String name;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Column(length = 500, nullable = false)
    private String description;

    @NotBlank(message = "La rareza o categoría es obligatoria")
    @Column(nullable = false)
    private String rarity; 

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El precio no puede ser negativo")
    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "original_price")
    private BigDecimal originalPrice; 

    @Column(name = "is_on_sale", nullable = false)
    private boolean isOnSale = false; 

    @NotBlank(message = "La URL de la imagen es obligatoria")
    @Column(name = "image_url", nullable = false)
    private String imageUrl; 
}