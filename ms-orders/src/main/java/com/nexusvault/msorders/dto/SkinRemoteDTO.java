package com.nexusvault.msorders.dto;

import java.math.BigDecimal;

public record SkinRemoteDTO(
    Long id,
    String nombre,
    BigDecimal precio,
    Boolean disponible
) {}