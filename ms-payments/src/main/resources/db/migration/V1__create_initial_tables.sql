CREATE TABLE payment_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL UNIQUE,
    amount_paid DECIMAL(19, 2) NOT NULL,
    payment_method ENUM('CREDIT_CARD', 'DEBIT_CARD', 'PAYPAL', 'NXP_WALLET', 'BANK_TRANSFER') NOT NULL,
    status ENUM('PENDING', 'PROCESSING', 'SUCCESS', 'FAILED', 'REFUNDED') NOT NULL,
    external_transaction_id VARCHAR(100),
    processed_at DATETIME
);