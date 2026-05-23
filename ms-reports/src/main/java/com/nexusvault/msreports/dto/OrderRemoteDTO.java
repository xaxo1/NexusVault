package com.nexusvault.msreports.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderRemoteDTO(
    Long id,
    Long userId,
    Long skinId,
    BigDecimal totalPago,
    LocalDateTime createdAt
) {}