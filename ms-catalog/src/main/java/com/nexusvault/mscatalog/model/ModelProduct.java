package com.nexusvault.mscatalog.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

//-------1-acá comienza con el schema general del modelo
/**
 * Representa la entidad de un producto o ítem en el catálogo.
 * Contiene información sobre su nombre, precio, rareza y estado de oferta.
 */
@Schema(description = "Entidad representativa de un ítem o producto comercializable dentro del catálogo de la plataforma")
@Entity
@Table(name = "products")
@Data
@NoArgsConstructor      
@AllArgsConstructor     
public class ModelProduct {

    //------2-acá comienza con el id
    @Schema(description = "Identificador único incremental autogenerado en base de datos", example = "1001")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nombre distintivo y único del ítem de la tienda", example = "Espada Rúnica del Alba")
    @NotBlank(message = "El nombre del producto es obligatorio")
    @Column(unique = true, nullable = false)
    private String name;

    @Schema(description = "Explicación detallada de las especificaciones y características del producto", example = "Forjada con fragmentos de meteorito e imbuida en magia de luz elemental.")
    @NotBlank(message = "La descripción no puede estar vacía")
    @Column(length = 500, nullable = false)
    private String description;

    @Schema(description = "Grado jerárquico de escasez del ítem", example = "EPIC")
    @NotNull(message = "La rareza es obligatoria")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rarity rarity;

    @Schema(description = "Valor monetario actual de comercialización del producto", example = "250.50")
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El precio no puede ser negativo")
    @Column(nullable = false)
    private BigDecimal price;

    @Schema(description = "Respaldo del costo base antes de aplicarse deducciones u ofertas", example = "300.00")
    @Column(name = "original_price")
    private BigDecimal originalPrice; 

    @Schema(description = "Estado lógico que indica si el producto goza de un descuento provisional", example = "true")
    @Column(name = "is_on_sale", nullable = false)
    private boolean isOnSale = false;

    @Schema(description = "Dirección HTTP de la imagen descriptiva del elemento", example = "https://assets.nexusvault.com/images/items/espada-alba.png")
    @NotBlank(message = "La URL de la imagen es obligatoria")
    @Column(name = "image_url", nullable = false)
    private String imageUrl; 
}