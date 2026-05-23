package com.nexusvault.msreports.controller;

import com.nexusvault.msreports.model.ModelReports;
import com.nexusvault.msreports.model.ReportType;
import com.nexusvault.msreports.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportsController {

    private final ReportService reportService;

    @GetMapping("/history")
    public ResponseEntity<List<ModelReports>> getAllReports() {
        return ResponseEntity.ok(reportService.obtenerTodosLosReportes());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ModelReports>> getByType(@RequestParam ReportType type) {
        return ResponseEntity.ok(reportService.obtenerReportesPorTipo(type));
    }
}