package com.nexusvault.mspayments.model;

/**
 * Enumeración de los diferentes estados en los que puede encontrarse una transacción o registro de pago.
 */
public enum PaymentStatus {
    PENDING,
    PROCESSING,
    SUCCESS,
    FAILED,
    REFUNDED
}