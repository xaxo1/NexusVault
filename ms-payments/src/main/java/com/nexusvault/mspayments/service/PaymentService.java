package com.nexusvault.mspayments.service;

import com.nexusvault.mspayments.dto.PaymentRequestDTO;
import com.nexusvault.mspayments.model.PaymentRecord;
import com.nexusvault.mspayments.model.PaymentStatus;

import java.util.List;

public interface PaymentService {
    PaymentRecord processPayment(PaymentRequestDTO request);
    PaymentRecord getPaymentByOrderId(Long orderId);
    List<PaymentRecord> getPaymentsByStatus(PaymentStatus status);
    PaymentRecord refundPayment(Long orderId);
}