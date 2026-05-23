package com.nexusvault.msreports.service;

import com.nexusvault.msreports.model.ModelReports;
import com.nexusvault.msreports.model.ReportType;
import java.util.List;

public interface ReportService {
    List<ModelReports> obtenerTodosLosReportes();
    List<ModelReports> obtenerReportesPorTipo(ReportType tipo);
}