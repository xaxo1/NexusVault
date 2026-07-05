package com.nexusvault.msorders.dto;

import java.math.BigDecimal;

/**
 * Representa los datos simplificados de un producto (Skin) obtenidos remotamente.
 *
 * @param id Identificador único de la skin.
 * @param nombre Nombre descriptivo.
 * @param precio Costo o precio unitario establecido.
 * @param disponible Bandera que indica si el producto está disponible.
 */
public record SkinRemoteDTO(
    Long id,
    String nombre,
    BigDecimal precio,
    Boolean disponible
) {}