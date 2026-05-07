-- Billetera para el Fundador (User 1) con saldo inicial
INSERT INTO wallets (user_id, saldo_actual, created_at, updated_at, is_active)
VALUES (1, 50000.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);

-- Billetera para el Usuario de pruebas (User 2)
INSERT INTO wallets (user_id, saldo_actual, created_at, updated_at, is_active)
VALUES (2, 1250.75, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);