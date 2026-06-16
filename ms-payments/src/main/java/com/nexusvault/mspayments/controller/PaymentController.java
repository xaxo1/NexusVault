package com.nexusvault.mspayments.controller;

import com.nexusvault.mspayments.dto.PaymentRequestDTO;
import com.nexusvault.mspayments.model.PaymentRecord;
import com.nexusvault.mspayments.model.PaymentStatus;
import com.nexusvault.mspayments.service.PaymentService;
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

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
//1-acá el tag general de la rúbrica
@Tag(name = "Pagos", description = "Endpoints para el procesamiento, consulta de transacciones externas y ejecución de reembolsos de la plataforma")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/process")
    @Operation(summary = "Procesar pago de una orden", description = "Recibe los datos de facturación de una orden, efectúa la llamada simulada a la pasarela bancaria externa y guarda el registro transaccional.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Transacción procesada y grabada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Payload de entrada inválido o montos fuera de regla de negocio", content = @Content),
        @ApiResponse(responseCode = "409", description = "La orden ya cuenta con un registro de pago asociado", content = @Content)
    })
    public ResponseEntity<PaymentRecord> processPayment(@Valid @RequestBody PaymentRequestDTO requestDTO) {
        PaymentRecord processedRecord = paymentService.processPayment(requestDTO);
        return new ResponseEntity<>(processedRecord, HttpStatus.CREATED);
    }

    @GetMapping("/order/{orderId}")
    @Operation(summary = "Buscar pago por ID de Orden", description = "Recupera la auditoría de pago asociada de forma unívoca a una orden de compra.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro de pago obtenido correctamente"),
        @ApiResponse(responseCode = "404", description = "No se halló ningún pago registrado para esa orden", content = @Content)
    })
    public ResponseEntity<PaymentRecord> getPaymentByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(paymentService.getPaymentByOrderId(orderId));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Filtrar transacciones por estado", description = "Permite a los administradores listar todos los pagos agrupados según su condición transaccional (SUCCESS, FAILED, REFUNDED).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado de pagos recuperado exitosamente")
    })
    public ResponseEntity<List<PaymentRecord>> getPaymentsByStatus(@PathVariable PaymentStatus status) {
        return ResponseEntity.ok(paymentService.getPaymentsByStatus(status));
    }

    @PutMapping("/refund/{orderId}")
    @Operation(summary = "Ejecutar el reembolso de un pago", description = "Modifica el estado de un pago exitoso a 'REFUNDED' para revertir el flujo de fondos asociado a la orden.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reembolso procesado de forma correcta"),
        @ApiResponse(responseCode = "404", description = "No existe registro de pago para esa orden", content = @Content),
        @ApiResponse(responseCode = "409", description = "El pago no se encuentra en estado exitoso para ser devuelto", content = @Content)
    })
    public ResponseEntity<PaymentRecord> refundPayment(@PathVariable Long orderId) {
        return ResponseEntity.ok(paymentService.refundPayment(orderId));
    }
}