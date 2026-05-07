-- Usuario 1: El administrador (Coincide con el ID 1 de ms-users y ms-wallet)
INSERT INTO auth_users (email, password, role, created_at, is_active)
VALUES ('admin@nexusvault.com', '$2a$10$EjemploHashEncriptadoAdmin', 'SUPER_ADMIN', CURRENT_TIMESTAMP, true);

-- Usuario 2: Un jugador normal (Coincide con el ID 2 de ms-users y ms-wallet)
INSERT INTO auth_users (email, password, role, created_at, is_active)
VALUES ('gamer@nexusvault.com', '$2a$10$EjemploHashEncriptadoGamer', 'USER', CURRENT_TIMESTAMP, true);