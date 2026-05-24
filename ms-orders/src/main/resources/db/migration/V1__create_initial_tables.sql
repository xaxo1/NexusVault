-- Primero creamos la tabla "maestra"
CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    total_amount DECIMAL(19, 2) NOT NULL,
    status ENUM('PENDING', 'PAID', 'SHIPPED', 'CANCELLED') NOT NULL,
    created_at DATETIME
);

-- Luego creamos la tabla "detalle"
CREATE TABLE order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price_at_purchase DECIMAL(19, 2) NOT NULL,
    order_id BIGINT,
    CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);