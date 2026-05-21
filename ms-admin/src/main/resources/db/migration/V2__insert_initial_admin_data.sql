-- Creamos un Administrador
INSERT INTO admins (name, email, role, active) 
VALUES ('Nikki Admin', 'admin@nexusvault.com', 'SUPER_ADMIN', true);

-- Registramos un evento en la bitácora: Nikki cambió el precio del Producto ID 1
INSERT INTO audit_logs (admin_id, action, target_entity, timestamp) 
VALUES (1, 'UPDATE_PRICE', 'PRODUCT_ID_1', CURRENT_TIMESTAMP);

-- Añadimos el detalle técnico exacto de lo que cambió
INSERT INTO audit_details (audit_log_id, field_name, old_value, new_value) 
VALUES (1, 'price', '2000.00', '1500.00'); // Asumiendo que el ID del log de auditoría es 1
