CREATE TABLE reports_history (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 requested_by_user_id BIGINT NOT NULL,
                                 tipo_reporte VARCHAR(50) NOT NULL,
                                 fecha_inicio_rango DATETIME NOT NULL,
                                 fecha_fin_rango DATETIME NOT NULL,
                                 total_ingresos_calculado DECIMAL(12, 2),
                                 pdf_file_url VARCHAR(500),
                                 fecha_generacion DATETIME NOT NULL
);