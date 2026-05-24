CREATE TABLE reports_history (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 requested_by_user_id BIGINT NOT NULL,
                                 tipo_reporte ENUM('VENTAS_MENSUALES','SKINS_MAS_VENDIDAS') NOT NULL,
                                 fecha_inicio_rango DATETIME NOT NULL,
                                 fecha_fin_rango DATETIME NOT NULL,
                                 total_ingresos_calculado DECIMAL(12, 2),
                                 pdf_file_url VARCHAR(500),
                                 fecha_generacion DATETIME NOT NULL
);