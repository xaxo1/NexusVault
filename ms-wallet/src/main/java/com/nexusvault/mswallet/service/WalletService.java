package com.nexusvault.mswallet.service;

import com.nexusvault.mswallet.model.ModelWallet;
import com.nexusvault.mswallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    public Optional<ModelWallet> getWalletByUserId(Long userId) {
        return walletRepository.findByUserId(userId);
    }

    /**
     * LÓGICA DE NEGOCIO: AGREGAR FONDOS (Depósitos)
     */
    public boolean addFunds(Long userId, BigDecimal amount) {
        // Validamos que no intenten depositar montos negativos o cero
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        Optional<ModelWallet> walletOpt = walletRepository.findByUserId(userId);

        if (walletOpt.isPresent()) {
            ModelWallet wallet = walletOpt.get();
            // BigDecimal usa .add() en lugar del signo +
            wallet.setSaldoActual(wallet.getSaldoActual().add(amount));
            walletRepository.save(wallet);
            return true;
        }

        return false;
    }

    /**
     * LÓGICA DE NEGOCIO: DESCONTAR FONDOS (Compras)
     * Este es el método que llamará ms-orders cuando un jugador compre una skin.
     */
    public boolean deductFunds(Long userId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        Optional<ModelWallet> walletOpt = walletRepository.findByUserId(userId);

        if (walletOpt.isPresent()) {
            ModelWallet wallet = walletOpt.get();

            // Verificamos si el saldo actual es MAYOR O IGUAL al monto a cobrar
            // compareTo devuelve 0 si son iguales, o 1 si el saldo es mayor
            if (wallet.getSaldoActual().compareTo(amount) >= 0) {
                // BigDecimal usa .subtract() en lugar del signo -
                wallet.setSaldoActual(wallet.getSaldoActual().subtract(amount));
                walletRepository.save(wallet);
                return true; // Compra aprobada
            }
        }

        return false; // Saldo insuficiente o billetera no encontrada
    }
}