-- Usuario 1: El administrador o fundador
INSERT INTO user_profiles (auth_id, nickname, reputacion, created_at, is_active)
VALUES (1, 'Nxs_Founder', 100, CURRENT_TIMESTAMP, true);

-- Usuario 2: Un usuario normal para pruebas
INSERT INTO user_profiles (auth_id, nickname, reputacion, created_at, is_active)
VALUES (2, 'GamerChileno', 50, CURRENT_TIMESTAMP, true);