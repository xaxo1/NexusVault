-- Simulamos el pago exitoso de la boleta #1 usando tarjeta de crédito
INSERT INTO payment_records (order_id, amount_paid, payment_method, status, external_transaction_id, processed_at)
VALUES (1, 1600.00, 'CREDIT_CARD', 'SUCCESS', 'TXN-987654321-NXP', CURRENT_TIMESTAMP);