-- Le damos 50 unidades de stock a la "Espada del Nexo" (product_id = 1)
INSERT INTO inventory (product_id, stock, last_updated) 
VALUES (1, 50, CURRENT_TIMESTAMP);

-- Le damos 100 unidades de stock al "Escudo de Madera" (product_id = 2)
INSERT INTO inventory (product_id, stock, last_updated) 
VALUES (2, 100, CURRENT_TIMESTAMP);