-- Reporte de ventas del mes pasado
INSERT INTO reports_history (requested_by_user_id, tipo_reporte, fecha_inicio_rango, fecha_fin_rango, total_ingresos_calculado, pdf_file_url, fecha_generacion)
VALUES (1, 'VENTAS_MENSUALES', '2026-04-01 00:00:00', '2026-04-30 23:59:59', 1500.50, 'https://s3.amazonaws.com/nexusvault/reports/rv_202604.pdf', CURRENT_TIMESTAMP);

-- Reporte de skins más vendidas
INSERT INTO reports_history (requested_by_user_id, tipo_reporte, fecha_inicio_rango, fecha_fin_rango, total_ingresos_calculado, pdf_file_url, fecha_generacion)
VALUES (1, 'SKINS_MAS_VENDIDAS', '2026-05-01 00:00:00', '2026-05-07 23:59:59', 850.00, NULL, CURRENT_TIMESTAMP);