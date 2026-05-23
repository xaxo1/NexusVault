package com.nexusvault.mswallet.controller;

import com.nexusvault.mswallet.dto.TransactionRequestDTO;
import com.nexusvault.mswallet.model.ModelWallet;
import com.nexusvault.mswallet.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    /**
     * CONSULTAR SALDO
     * Útil para mostrarle al jugador cuánto dinero tiene en el Navbar de la web.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getBalance(@PathVariable Long userId) {
        Optional<ModelWallet> wallet = walletService.getWalletByUserId(userId);

        if (wallet.isPresent()) {
            return ResponseEntity.ok(wallet.get());
        } else {
            return ResponseEntity.status(404).body("No se encontró una billetera para el usuario ID: " + userId);
        }
    }

    /**
     * DEPOSITAR DINERO
     * Simula la carga de fondos (por ejemplo, después de un pago exitoso).
     */
    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@Valid @RequestBody TransactionRequestDTO transactionRequest) {
        boolean success = walletService.addFunds(transactionRequest.getUserId(), transactionRequest.getAmount());

        if (success) {
            return ResponseEntity.ok("Depósito realizado con éxito. Nuevo saldo actualizado.");
        } else {
            return ResponseEntity.status(400).body("Error al procesar el depósito. Verifique el monto o el ID de usuario.");
        }
    }

    /**
     * COBRAR COMPRA (Deducción)
     * Este endpoint será el que golpee el ms-orders para validar la transacción.
     */
    @PostMapping("/pay")
    public ResponseEntity<String> processPayment(@Valid @RequestBody TransactionRequestDTO transactionRequest) {
        boolean success = walletService.deductFunds(transactionRequest.getUserId(), transactionRequest.getAmount());

        if (success) {
            return ResponseEntity.ok("Pago procesado correctamente. ¡Skin comprada!");
        } else {
            return ResponseEntity.status(402).body("Pago rechazado: Saldo insuficiente o cuenta no encontrada.");
        }
    }
}