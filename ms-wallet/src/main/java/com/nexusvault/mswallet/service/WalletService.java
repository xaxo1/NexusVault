package com.nexusvault.mswallet.service;

import com.nexusvault.mswallet.model.ModelWallet;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * Contrato de servicio que dicta las normas y operaciones de negocio para procesar los fondos de las billeteras digitales.
 */
public interface WalletService {
    /**
     * Recupera en modo lectura la billetera asociada a un usuario en la plataforma.
     *
     * @param userId El ID del usuario.
     * @return Un objeto {@link Optional} que envuelve la billetera si está registrada.
     */
    Optional<ModelWallet> getWalletByUserId(Long userId);
    /**
     * Realiza un abono o incremento positivo al saldo actual del usuario en base al monto ingresado.
     *
     * @param userId El identificador único del usuario beneficiario del depósito.
     * @param amount La cuantía económica específica a depositar (mayor a cero).
     * @return true si la recarga se procesó de forma correcta; false si el depósito falló o es inválido.
     */
    boolean addFunds(Long userId, BigDecimal amount);
    /**
     * Disminuye de forma transaccional el monto solicitado si el saldo existente en la billetera es igual o superior.
     *
     * @param userId El ID del usuario a quien se le realizará el cobro.
     * @param amount La cifra exacta que el sistema requiere descontar.
     * @return true si la deducción fue exitosa; false si hubo saldo insuficiente, la cuenta no existe o el monto es incorrecto.
     */
    boolean deductFunds(Long userId, BigDecimal amount);
}