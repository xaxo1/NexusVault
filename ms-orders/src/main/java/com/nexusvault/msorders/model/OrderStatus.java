package com.nexusvault.msorders.model;

/**
 * Enumeración con los posibles estados por los que puede atravesar una orden.
 */
public enum OrderStatus {
    PENDING,
    PAID,
    SHIPPED,
    CANCELLED
}