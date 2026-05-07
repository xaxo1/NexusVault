-- 1. Tabla de Administradores
CREATE TABLE admins (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    role VARCHAR(50),
    active BOOLEAN NOT NULL DEFAULT TRUE
);

-- 2. Tabla del Encabezado de la Bitácora
CREATE TABLE audit_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    admin_id BIGINT,
    action VARCHAR(255),
    target_entity VARCHAR(255),
    timestamp DATETIME
);

-- 3. Tabla de Detalles (Con Llave Foránea)
CREATE TABLE audit_details (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    audit_log_id BIGINT,
    field_name VARCHAR(255),
    old_value VARCHAR(255),
    new_value VARCHAR(255),
    CONSTRAINT fk_audit_log FOREIGN KEY (audit_log_id) REFERENCES audit_logs(id) ON DELETE CASCADE
);