package com.nexusvault.msorders.dto;

import java.math.BigDecimal;

/**
 * Representa la información remota de la billetera (Wallet) de un usuario.
 *
 * @param id Identificador único de la billetera.
 * @param userId Identificador del usuario propietario.
 * @param saldoActual Saldo disponible en la cuenta.
 * @param isActive Indica si la billetera está activa y operativa.
 */
public record WalletRemoteDTO(
    Long id,
    Long userId,
    BigDecimal saldoActual,
    Boolean isActive
) {}