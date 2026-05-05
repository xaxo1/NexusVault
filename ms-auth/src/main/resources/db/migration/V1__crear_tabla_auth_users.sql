CREATE TABLE auth_users (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            email VARCHAR(100) NOT NULL UNIQUE,
                            password VARCHAR(255) NOT NULL,
                            role VARCHAR(20) NOT NULL,
                            created_at DATETIME,
                            is_active BOOLEAN NOT NULL
);