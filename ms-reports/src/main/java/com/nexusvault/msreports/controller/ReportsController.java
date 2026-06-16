package com.nexusvault.msreports.controller;

import com.nexusvault.msreports.model.ModelReports;
import com.nexusvault.msreports.model.ReportType;
import com.nexusvault.msreports.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
//1-acá el tag general de la rúbrica
@Tag(name = "Reportes", description = "Endpoints para la gestión del historial de auditoría analítica y disparo de flujos distribuidos de generación de balances")
public class ReportsController {

    private final ReportService reportService;

    @GetMapping("/history")
    @Operation(summary = "Obtener historial completo de reportes", description = "Recupera la lista histórica de todos los informes consolidados en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Historial analítico recuperado con éxito")
    })
    public ResponseEntity<List<ModelReports>> getAllReports() {
        return ResponseEntity.ok(reportService.obtenerTodosLosReportes());
    }

    @GetMapping("/search")
    @Operation(summary = "Filtrar reportes por tipo", description = "Busca informes almacenados agrupados por su tipología específica (ej. VENTAS_MENSUALES).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de reportes filtrada obtenida correctamente"),
        @ApiResponse(responseCode = "400", description = "Tipo de reporte especificado no válido", content = @Content)
    })
    public ResponseEntity<List<ModelReports>> getByType(@RequestParam ReportType type) {
        return ResponseEntity.ok(reportService.obtenerReportesPorTipo(type));
    }

    @PostMapping("/generate")
    @Operation(summary = "Generar reporte financiero de forma asíncrona", description = "Dispara el flujo reactivo de recolección de información. Consulta el perfil en ms-users, el listado de compras en ms-orders, calcula los ingresos netos y archiva el resultado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reporte compilado y guardado correctamente en la base de datos"),
        @ApiResponse(responseCode = "400", description = "Error en el procesamiento o datos de entrada inconsistentes", content = @Content)
    })
    public Mono<ResponseEntity<ModelReports>> generateReport(@RequestParam Long userId) {
        return reportService.generarYGuardarReporteFinancieroAsync(userId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}