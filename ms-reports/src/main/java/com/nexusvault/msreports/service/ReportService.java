package com.nexusvault.msreports.service;

import com.nexusvault.msreports.model.ModelReports;
import com.nexusvault.msreports.model.ReportType;
import reactor.core.publisher.Mono;
import java.util.List;

/**
 * Interfaz de servicio que define las operaciones de negocio para la gestión y generación de reportes.
 */
public interface ReportService {
    /**
     * Obtiene el historial completo de todos los reportes existentes.
     *
     * @return Una lista de entidades {@link ModelReports}.
     */
    List<ModelReports> obtenerTodosLosReportes();
    /**
     * Obtiene los reportes filtrados por su tipo específico.
     *
     * @param tipo El tipo de reporte según el enumerador {@link ReportType}.
     * @return Una lista de reportes que corresponden al tipo dado.
     */
    List<ModelReports> obtenerReportesPorTipo(ReportType tipo);
    
    /**
     * Genera y persiste un reporte financiero de manera asíncrona.
     * Este proceso incluye la recolección de información desde diferentes microservicios (usuarios, órdenes).
     *
     * @param requestedByUserId El identificador del usuario que solicita la generación del reporte.
     * @return Un objeto {@link Mono} que emite el reporte financiero generado una vez es guardado con éxito.
     */
    // 👈 NUEVO MÉTODO REACTIVO EXIGIDO POR LA RÚBRICA
    Mono<ModelReports> generarYGuardarReporteFinancieroAsync(Long requestedByUserId);
}