package com.nexusvault.msreports.controller;

import com.nexusvault.msreports.model.ModelReports;
import com.nexusvault.msreports.model.ReportType;
import com.nexusvault.msreports.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

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

    // 👈 NUEVO ENDPOINT PARA DISPARAR LA ORQUESTACIÓN ASÍNCRONA
    @PostMapping("/generate")
    public Mono<ResponseEntity<ModelReports>> generateReport(@RequestParam Long userId) {
        return reportService.generarYGuardarReporteFinancieroAsync(userId)
                .map(reporteGuardado -> ResponseEntity.ok(reporteGuardado))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}