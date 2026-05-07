CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(500) NOT NULL,
    rarity VARCHAR(50) NOT NULL,
    price DECIMAL(19, 2) NOT NULL,
    original_price DECIMAL(19, 2),
    is_on_sale BOOLEAN NOT NULL DEFAULT FALSE,
    image_url VARCHAR(255) NOT NULL
);