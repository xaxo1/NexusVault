package com.nexusvault.mspayments.controller;

import com.nexusvault.mspayments.dto.PaymentRequestDTO;
import com.nexusvault.mspayments.model.PaymentRecord;
import com.nexusvault.mspayments.model.PaymentStatus;
import com.nexusvault.mspayments.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/process")
    public ResponseEntity<PaymentRecord> processPayment(@Valid @RequestBody PaymentRequestDTO requestDTO) {
        PaymentRecord processedRecord = paymentService.processPayment(requestDTO);
        return new ResponseEntity<>(processedRecord, HttpStatus.CREATED);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentRecord> getPaymentByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(paymentService.getPaymentByOrderId(orderId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PaymentRecord>> getPaymentsByStatus(@PathVariable PaymentStatus status) {
        return ResponseEntity.ok(paymentService.getPaymentsByStatus(status));
    }

    @PutMapping("/refund/{orderId}")
    public ResponseEntity<PaymentRecord> refundPayment(@PathVariable Long orderId) {
        return ResponseEntity.ok(paymentService.refundPayment(orderId));
    }
}