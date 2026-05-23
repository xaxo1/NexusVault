package com.nexusvault.msreports.service;

import com.nexusvault.msreports.model.ModelReports;
import com.nexusvault.msreports.model.ReportType;
import reactor.core.publisher.Mono;
import java.util.List;

public interface ReportService {
    List<ModelReports> obtenerTodosLosReportes();
    List<ModelReports> obtenerReportesPorTipo(ReportType tipo);
    
    // 👈 NUEVO MÉTODO REACTIVO EXIGIDO POR LA RÚBRICA
    Mono<ModelReports> generarYGuardarReporteFinancieroAsync(Long requestedByUserId);
}