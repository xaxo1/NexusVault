CREATE TABLE inventory (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL UNIQUE,
    stock INT NOT NULL CHECK (stock >= 0),
    last_updated DATETIME
);