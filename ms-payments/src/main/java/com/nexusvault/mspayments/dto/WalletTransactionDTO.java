package com.nexusvault.mspayments.dto;

import java.math.BigDecimal;

/**
 * Objeto para transportar la información remota al intentar depositar o sincronizar fondos en la billetera.
 *
 * @param userId Identificador único del usuario destino.
 * @param amount Cantidad o importe del movimiento.
 */
public record WalletTransactionDTO(
    Long userId,
    BigDecimal amount
) {}