-- Insertamos la Orden (boleta)
INSERT INTO orders (user_id, total_amount, status, created_at)
VALUES (1, 1600.00, 'PENDING', CURRENT_TIMESTAMP);

-- Insertamos la Espada a esa orden (order_id = 1)
INSERT INTO order_items (product_id, quantity, price_at_purchase, order_id)
VALUES (1, 1, 1500.00, 1);

-- Insertamos el Escudo a esa orden (order_id = 1)
INSERT INTO order_items (product_id, quantity, price_at_purchase, order_id)
VALUES (2, 1, 100.00, 1);