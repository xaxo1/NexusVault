package com.nexusvault.mspayments.service.impl;

import com.nexusvault.mspayments.dto.PaymentRequestDTO;
import com.nexusvault.mspayments.model.PaymentRecord;
import com.nexusvault.mspayments.model.PaymentStatus;
import com.nexusvault.mspayments.repository.PaymentRecordRepository;
import com.nexusvault.mspayments.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRecordRepository paymentRepository;

    @Override
    @Transactional
    public PaymentRecord processPayment(PaymentRequestDTO request) {
        log.info("Iniciando procesamiento de pago para la orden ID: {}", request.getOrderId());

        // Regla de Negocio: No procesar un pago si la orden ya posee un registro de pago previo
        paymentRepository.findByOrderId(request.getOrderId()).ifPresent(p -> {
            throw new IllegalStateException("Ya existe un registro de pago asociado a la orden ID: " + request.getOrderId());
        });

        PaymentRecord record = new PaymentRecord();
        record.setOrderId(request.getOrderId());
        record.setAmountPaid(request.getAmount());
        record.setPaymentMethod(request.getPaymentMethod());
        
        try {
            log.info("Conectando con la pasarela externa para el método de pago: {}", request.getPaymentMethod());
            
            // Simulación técnica de una respuesta exitosa de pasarela externa
            // (Nota: En producción, aquí integrarías RestTemplate o WebClient hacia PayPal/Transbank)
            String externalTxId = "NEXUS-TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            
            record.setExternalTransactionId(externalTxId);
            record.setStatus(PaymentStatus.SUCCESS);
            log.info("Transacción aprobada externamente de forma exitosa. TXN ID: {}", externalTxId);
            
        } catch (Exception e) {
            record.setStatus(PaymentStatus.FAILED);
            log.error("Fallo crítico al procesar la pasarela de pagos para la orden ID: {}", request.getOrderId(), e);
        }

        return paymentRepository.save(record);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentRecord getPaymentByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró ningún registro de pago para la orden ID: " + orderId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentRecord> getPaymentsByStatus(PaymentStatus status) {
        log.info("Consultando registros de pago bajo el estado: {}", status);
        return paymentRepository.findByStatus(status);
    }

    @Override
    @Transactional
    public PaymentRecord refundPayment(Long orderId) {
        PaymentRecord record = getPaymentByOrderId(orderId);
        
        if (record.getStatus() != PaymentStatus.SUCCESS) {
            throw new IllegalStateException("Solo se pueden reembolsar pagos que se hayan completado con éxito (SUCCESS). Estado actual: " + record.getStatus());
        }
        
        record.setStatus(PaymentStatus.REFUNDED);
        log.info("El pago de la orden ID: {} ha sido REEMBOLSADO con éxito.", orderId);
        return paymentRepository.save(record);
    }
}