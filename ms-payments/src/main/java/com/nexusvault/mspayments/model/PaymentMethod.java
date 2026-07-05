package com.nexusvault.mspayments.model;

/**
 * Enumeración que describe los métodos y vías soportadas para realizar transacciones.
 */
public enum PaymentMethod {
    CREDIT_CARD,
    DEBIT_CARD,
    PAYPAL,
    NXP_WALLET,
    BANK_TRANSFER
}