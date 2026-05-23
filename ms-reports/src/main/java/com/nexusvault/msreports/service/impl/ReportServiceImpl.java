package com.nexusvault.msreports.service.impl;

import com.nexusvault.msreports.model.ModelReports;
import com.nexusvault.msreports.model.ReportType;
import com.nexusvault.msreports.repository.ReportsRepository;
import com.nexusvault.msreports.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportsRepository reportsRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ModelReports> obtenerTodosLosReportes() {
        return reportsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ModelReports> obtenerReportesPorTipo(ReportType tipo) {
        return reportsRepository.findByTipoReporte(tipo);
    }
}