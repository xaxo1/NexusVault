CREATE TABLE wallets (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         user_id BIGINT NOT NULL UNIQUE,
                         saldo_actual DECIMAL(10, 2) NOT NULL,
                         cuenta_bancaria_vinculada VARCHAR(100),
                         created_at DATETIME NOT NULL,
                         updated_at DATETIME,
                         is_active BOOLEAN NOT NULL
);