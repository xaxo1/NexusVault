-- 1. Tabla admins (Basado en la clase Admin)
CREATE TABLE admins (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        email VARCHAR(255) UNIQUE,
                        role VARCHAR(255),
                        active BOOLEAN NOT NULL DEFAULT TRUE
);

-- 2. Tabla audit_logs (Basado en la clase AuditLog)
CREATE TABLE audit_logs (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            admin_id BIGINT,
                            action VARCHAR(255),
                            target_entity VARCHAR(255),
                            timestamp DATETIME
);

-- 3. Tabla audit_details (Basado en la clase AuditDetail)
CREATE TABLE audit_details (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               audit_log_id BIGINT,
                               field_name VARCHAR(255),
                               old_value VARCHAR(255),
                               new_value VARCHAR(255),
                               CONSTRAINT fk_audit_log FOREIGN KEY (audit_log_id) REFERENCES audit_logs(id) ON DELETE CASCADE
);