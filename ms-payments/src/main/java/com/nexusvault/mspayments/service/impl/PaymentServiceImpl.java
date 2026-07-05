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

/**
 * Implementación de las reglas de negocio del servicio de pagos.
 * Controla la simulación de cobro mediante pasarelas externas, previniendo duplicados 
 * y gestionando de forma atómica los cambios transaccionales.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRecordRepository paymentRepository;

    /**
     * Procesa la solicitud de cobro simulando la integración con una pasarela bancaria.
     * Evita la duplicación de pagos verificando la orden con anterioridad.
     *
     * @param request Información requerida para cobrar (ID de orden, método de pago, monto).
     * @return El registro de pago persistido, idealmente con estado SUCCESS.
     * @throws IllegalStateException si la orden ya ha sido pagada previamente.
     */
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

    /**
     * Busca la transacción de pago subyacente para el ID de una orden específica.
     *
     * @param orderId El identificador de la orden.
     * @return El registro de auditoría de pago.
     * @throws IllegalArgumentException si la orden carece de pago en el sistema.
     */
    @Override
    @Transactional(readOnly = true)
    public PaymentRecord getPaymentByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró ningún registro de pago para la orden ID: " + orderId));
    }

    /**
     * Recupera una lista agrupando todos los pagos por estado específico.
     *
     * @param status El estado de interés (ej. SUCCESS, FAILED).
     * @return Una colección de registros de pago coincidentes.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PaymentRecord> getPaymentsByStatus(PaymentStatus status) {
        log.info("Consultando registros de pago bajo el estado: {}", status);
        return paymentRepository.findByStatus(status);
    }

    /**
     * Ejecuta de forma lógica un reembolso o contracargo, actualizando el estado de la operación.
     * Solo permite ejecutar el flujo si el estado actual es SUCCESS.
     *
     * @param orderId Identificador de la orden a devolver.
     * @return El registro de pago actualizado al estado REFUNDED.
     * @throws IllegalStateException si el pago no es exitoso y no puede ser reembolsado.
     */
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