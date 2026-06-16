CREATE TABLE admins (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50),
    active BOOLEAN DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE audit_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    admin_id BIGINT,
    action VARCHAR(255) NOT NULL,
    target_entity VARCHAR(255) NOT NULL,
    timestamp DATETIME NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE audit_details (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    audit_log_id BIGINT,
    field_name VARCHAR(255),
    old_value TEXT,
    new_value TEXT,
    CONSTRAINT fk_audit_details_log FOREIGN KEY (audit_log_id) 
        REFERENCES audit_logs(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;