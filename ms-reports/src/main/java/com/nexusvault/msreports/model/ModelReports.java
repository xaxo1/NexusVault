package com.nexusvault.msreports.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

//-------1-acá comienza con el schema general del modelo
@Schema(description = "Entidad inmutable que representa un registro consolidado de auditoría analítica y financiera")
@Entity
@Table(name = "reports_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModelReports {

    //------2-acá comienza con el id
    @Schema(description = "Identificador único y autoincremental del reporte", example = "501")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "ID del administrador o analista que solicitó la compilación de datos", example = "1002")
    @Column(name = "requested_by_user_id", nullable = false)
    private Long requestedByUserId;

    @Schema(description = "Clasificación temática del informe generado", example = "VENTAS_MENSUALES")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_reporte", nullable = false, length = 50, updatable = false)
    private ReportType tipoReporte;

    @Schema(description = "Fecha inicial del rango cronológico de los datos evaluados", example = "2026-05-01T00:00:00")
    @Column(name = "fecha_inicio_rango", nullable = false, updatable = false)
    private LocalDateTime fechaInicioRango;

    @Schema(description = "Fecha límite del rango cronológico de los datos evaluados", example = "2026-05-31T23:59:59")
    @Column(name = "fecha_fin_rango", nullable = false, updatable = false)
    private LocalDateTime fechaFinRango;

    @Schema(description = "Suma total acumulada calculada a partir de los registros remotos", example = "1450.75")
    @Column(name = "total_ingresos_calculado", precision = 12, scale = 2, updatable = false)
    private BigDecimal totalIngresosCalculado;

    @Schema(description = "Enlace físico de descarga del documento exportado (ej. AWS S3)", example = "https://nexusvault-s3.amazonaws.com/reports/report-1718549021.pdf")
    @Column(name = "pdf_file_url", length = 500)
    private String pdfFileUrl;

    @Schema(description = "Timestamp exacto del almacenamiento físico del registro", example = "2026-06-16T18:00:00")
    @Column(name = "fecha_generacion", nullable = false, updatable = false)
    private LocalDateTime fechaGeneracion;

    @PrePersist
    protected void onCreate() {
        this.fechaGeneracion = LocalDateTime.now();
        if (this.totalIngresosCalculado == null) {
            this.totalIngresosCalculado = BigDecimal.ZERO;
        }
    }
}