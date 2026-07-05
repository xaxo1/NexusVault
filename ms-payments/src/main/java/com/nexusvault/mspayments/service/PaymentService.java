package com.nexusvault.mspayments.service;

import com.nexusvault.mspayments.dto.PaymentRequestDTO;
import com.nexusvault.mspayments.model.PaymentRecord;
import com.nexusvault.mspayments.model.PaymentStatus;

import java.util.List;

/**
 * Interfaz para el servicio principal de gestión de cobros y pagos.
 * Centraliza el flujo para procesar transacciones, generar reembolsos y realizar consultas.
 */
public interface PaymentService {
    /**
     * Inicia y registra el proceso de cobro de una orden.
     *
     * @param request Datos del pago.
     * @return El registro del pago resultante.
     */
    PaymentRecord processPayment(PaymentRequestDTO request);

    /**
     * Retorna la información de pago específica para una orden dada.
     *
     * @param orderId Identificador de la orden.
     * @return El registro de pago asociado.
     */
    PaymentRecord getPaymentByOrderId(Long orderId);

    /**
     * Devuelve una lista de transacciones basada en su estado final.
     *
     * @param status Estado transaccional (ej: REFUNDED, SUCCESS).
     * @return Lista de registros de pagos.
     */
    List<PaymentRecord> getPaymentsByStatus(PaymentStatus status);

    /**
     * Emite una devolución de dinero para una orden que previamente se cobró de forma exitosa.
     *
     * @param orderId Identificador de la orden.
     * @return El registro de pago ya actualizado con el estado reembolsado.
     */
    PaymentRecord refundPayment(Long orderId);
}