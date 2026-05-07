CREATE TABLE payment_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL UNIQUE,
    amount_paid DECIMAL(19, 2) NOT NULL,
    payment_method ENUM('credit_card', 'debit_card', 'paypal', 'nxp_wallet', 'bank_transfer') NOT NULL,
    status ENUM('PENDING', 'PROCESSING', 'SUCCESS', 'FAILED', 'REFUNDED') NOT NULL,
    external_transaction_id VARCHAR(100),
    processed_at DATETIME
);