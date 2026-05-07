-- 1. Notificación pendiente de envío
INSERT INTO notifications_log (user_id, target_email, title, message, status, created_at)
VALUES (1, 'nikki@nexusvault.com', 'Bienvenido a Nexus Vault', 'Gracias por unirte al mejor marketplace de ítems. ¡Prepara tu inventario!', 'PENDING', CURRENT_TIMESTAMP);

-- 2. Notificación ya enviada (simulando que el sistema ya la despachó)
INSERT INTO notifications_log (user_id, target_email, title, message, status, created_at, sent_at)
VALUES (1, 'nikki@nexusvault.com', 'Alerta de Seguridad', 'Se ha iniciado sesión desde un nuevo dispositivo.', 'SENT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);