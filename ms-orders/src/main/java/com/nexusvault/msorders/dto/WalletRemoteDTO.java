package com.nexusvault.msorders.dto;

import java.math.BigDecimal;

public record WalletRemoteDTO(
    Long id,
    Long userId,
    BigDecimal saldoActual,
    Boolean isActive
) {}