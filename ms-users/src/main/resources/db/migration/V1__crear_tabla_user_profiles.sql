CREATE TABLE user_profiles (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               auth_id BIGINT NOT NULL UNIQUE,
                               nickname VARCHAR(50) NOT NULL UNIQUE,
                               reputacion INT NOT NULL,
                               created_at DATETIME NOT NULL,
                               is_active BOOLEAN NOT NULL
);