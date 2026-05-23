package com.nexusvault.msreports.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "reports_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModelReports {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * [TRAZABILIDAD DE USUARIO]
     * Guarda el ID del Analista o Administrador que solicitó este reporte.
     * No es 'unique = true' porque un mismo analista puede generar cientos de reportes.
     */
    @Column(name = "requested_by_user_id", nullable = false)
    private Long requestedByUserId;

    /*
     * [CLASIFICACIÓN DEL REPORTE]
     * Define de qué trata el documento. Ejemplos: "VENTAS_MENSUALES", "SKINS_MAS_VENDIDAS".
     * 'updatable = false' porque el tipo de un reporte histórico nunca debe cambiar.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_reporte", nullable = false, length = 50, updatable = false)
    private ReportType tipoReporte;

    /*
     * [PARÁMETROS DE BÚSQUEDA - SEGÚN DIAGRAMA UML]
     * Definen el rango de tiempo que abarcó este reporte.
     * Esto evita que gerencia se confunda y sepa exactamente de qué periodo son los datos.
     */
    @Column(name = "fecha_inicio_rango", nullable = false, updatable = false)
    private LocalDateTime fechaInicioRango;

    @Column(name = "fecha_fin_rango", nullable = false, updatable = false)
    private LocalDateTime fechaFinRango;

    /*
     * [CACHÉ DE RENDIMIENTO FINANCIERO]
     * En lugar de calcular los ingresos cada vez que miramos el historial,
     * guardamos el resultado final del cálculo aquí (usando BigDecimal por precisión).
     * Esto ahorra recursos del servidor y cumple con el requisito de 'rendimiento' de tu informe.
     */
    @Column(name = "total_ingresos_calculado", precision = 12, scale = 2, updatable = false)
    private BigDecimal totalIngresosCalculado;

    /*
     * [UBICACIÓN DEL ARCHIVO]
     * (Opcional pero muy profesional)
     * Si el PDF se guarda en un servidor (como AWS S3), aquí guardas la ruta
     * para que el usuario pueda volver a descargarlo sin tener que generarlo de nuevo.
     */
    @Column(name = "pdf_file_url", length = 500)
    private String pdfFileUrl;

    /*
     * [AUDITORÍA ESTRICTA]
     * Fecha exacta en la que el sistema terminó de procesar el PDF.
     */
    @Column(name = "fecha_generacion", nullable = false, updatable = false)
    private LocalDateTime fechaGeneracion;

    /*
     * [TRIGGER DE INICIALIZACIÓN]
     * Registra la fecha de generación de forma automática.
     * Fíjate que en este modelo NO hay @PreUpdate. Esto es intencional.
     * En bases de datos de auditoría, los registros son inmutables (solo de lectura/inserción).
     */
    @PrePersist
    protected void onCreate() {
        this.fechaGeneracion = LocalDateTime.now();
        if (this.totalIngresosCalculado == null) {
            this.totalIngresosCalculado = BigDecimal.ZERO;
        }
    }
}