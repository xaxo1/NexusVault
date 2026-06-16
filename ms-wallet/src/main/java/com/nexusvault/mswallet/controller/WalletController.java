package com.nexusvault.mswallet.controller;

import com.nexusvault.mswallet.dto.TransactionRequestDTO;
import com.nexusvault.mswallet.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
@RequiredArgsConstructor
//1-acá el tag general de la rúbrica
@Tag(name = "Billeteras", description = "Endpoints para la gestión de balances, abonos de saldo y cobros transaccionales de jugadores")
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/user/{userId}")
    @Operation(summary = "Consultar estado y saldo actual", description = "Recupera la información completa de la billetera del jugador, útil para el Navbar del cliente web.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estructura de la billetera localizada con éxito"),
        @ApiResponse(responseCode = "404", description = "No existe ninguna billetera asociada al usuario especificado", content = @Content)
    })
    public ResponseEntity<?> getBalance(@PathVariable Long userId) {
        return walletService.getWalletByUserId(userId)
                .map(wallet -> ResponseEntity.ok((Object) wallet))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontró una billetera para el usuario ID: " + userId));
    }

    @PostMapping("/deposit")
    @Operation(summary = "Depositar fondos en la cuenta", description = "Simula e incrementa los fondos del balance actual de un jugador tras realizar una recarga monetaria exitosa.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Depósito procesado de manera correcta, balance actualizado"),
        @ApiResponse(responseCode = "400", description = "Monto inválido, negativo o datos del usuario erróneos", content = @Content)
    })
    public ResponseEntity<String> deposit(@Valid @RequestBody TransactionRequestDTO transactionRequest) {
        boolean success = walletService.addFunds(transactionRequest.getUserId(), transactionRequest.getAmount());
        if (success) {
            return ResponseEntity.ok("Depósito realizado con éxito. Nuevo saldo actualizado.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al procesar el depósito. Verifique el monto o el ID de usuario.");
        }
    }

    @PostMapping("/pay")
    @Operation(summary = "Procesar pago por compra de skin", description = "Realiza la deducción del saldo de un jugador para aprobar una transacción distribuida iniciada por ms-orders.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Monto descontado correctamente. ¡Transacción completada!"),
        @ApiResponse(responseCode = "402", description = "Pago rechazado: El balance del usuario es insuficiente para el cobro", content = @Content)
    })
    public ResponseEntity<String> processPayment(@Valid @RequestBody TransactionRequestDTO transactionRequest) {
        boolean success = walletService.deductFunds(transactionRequest.getUserId(), transactionRequest.getAmount());
        if (success) {
            return ResponseEntity.ok("Pago procesado correctamente. ¡Skin comprada!");
        } else {
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED)
                    .body("Pago rechazado: Saldo insuficiente o cuenta no encontrada.");
        }
    }
}