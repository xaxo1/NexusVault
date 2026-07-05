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

/**
 * Controlador REST encargado de exponer los endpoints de gestión de las billeteras (wallets) de los usuarios.
 */
@RestController
@RequestMapping("/api/wallet")
@RequiredArgsConstructor
//1-acá el tag general de la rúbrica
@Tag(name = "Billeteras", description = "Endpoints para la gestión de balances, abonos de saldo y cobros transaccionales de jugadores")
public class WalletController {

    private final WalletService walletService;

    /**
     * Obtiene el saldo actual y el estado completo de la billetera de un usuario determinado.
     *
     * @param userId El identificador del usuario propietario de la billetera.
     * @return Una respuesta HTTP con los datos de la billetera, o código 404 si no existe.
     */
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

    /**
     * Procesa de manera segura un depósito de fondos en la billetera de un usuario.
     *
     * @param transactionRequest Objeto DTO que contiene el ID de usuario y el monto monetario a depositar.
     * @return Una respuesta HTTP confirmando el éxito de la transacción, o 400 si hubo un error o monto inválido.
     */
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

    /**
     * Realiza un cobro o deducción de fondos en la billetera de un usuario, requerido habitualmente por otros microservicios.
     *
     * @param transactionRequest Objeto DTO que especifica el ID del usuario y el costo a deducir de su saldo.
     * @return Una respuesta HTTP confirmando la deducción, o 402 si el balance actual es insuficiente.
     */
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