package com.nexusvault.mspayments.dto;

import java.math.BigDecimal;

public record WalletTransactionDTO(
    Long userId,
    BigDecimal amount
) {}