package com.nexusvault.msadmin.controller;

import com.nexusvault.msadmin.model.AuditLog;
import com.nexusvault.msadmin.service.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nexusvault.msadmin.model.AuditDetail;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@RestController
@RequestMapping("/api/v1/audits")
@RequiredArgsConstructor
//1-acá el tag general
@Tag(name = "Auditoría (Logs)", description = "Endpoints destinados a la trazabilidad y registro de logs de auditoría técnica en la plataforma")
public class AuditController {

    private final AuditService auditService;

    //3-Operation
    @Operation(summary = "Crear registro de auditoría", description = "Inserta una nueva traza de auditoría que documenta una acción realizada por un administrador sobre una entidad.")
    //4-API Responses
    @ApiResponses(value = {
        // 4.1  Response
        @ApiResponse(responseCode = "201", description = "Log de auditoría registrado con éxito")
    })
    @PostMapping
    public ResponseEntity<AuditLog> createAuditLog(@RequestBody AuditCreateRequest request) {
        AuditLog newLog = auditService.createAuditRecord(
                request.getAdminId(),
                request.getAction(),
                request.getTargetEntity(),
                request.getDetails()
        );
        return new ResponseEntity<>(newLog, HttpStatus.CREATED);
    }

    //5-Operation
    @Operation(summary = "Obtener logs por Administrador", description = "Filtra e informa todas las acciones históricas ejecutadas por un administrador específico.")
    //6-API Responses
    @ApiResponses(value = {
        // 6.1  Response
        @ApiResponse(responseCode = "200", description = "Historial obtenido correctamente")
    })
    @GetMapping("/admin/{adminId}")
    public ResponseEntity<List<AuditLog>> getLogsByAdmin(@PathVariable Long adminId) {
        List<AuditLog> logs = auditService.getLogsByAdmin(adminId);
        return ResponseEntity.ok(logs);
    }

    @Operation(summary = "Obtener logs por Entidad Objetivo", description = "Permite auditar los cambios sufridos en un módulo específico del sistema (ej: PRODUCT, USER).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista filtrada por entidad de destino recuperada")
    })
    @GetMapping("/entity/{targetEntity}")
    public ResponseEntity<List<AuditLog>> getLogsByEntity(@PathVariable String targetEntity) {
        List<AuditLog> logs = auditService.getLogsByEntity(targetEntity);
        return ResponseEntity.ok(logs);
    }

    @Data
    @Schema(description = "Modelo de petición requerido para inicializar un registro de auditoría")
    public static class AuditCreateRequest {
        @Schema(description = "ID del administrador ejecutor", example = "10")
        private Long adminId;
        @Schema(description = "Acción técnica realizada", example = "UPDATE_PRICE")
        private String action;
        @Schema(description = "Entidad afectada", example = "SKIN_CATALOG")
        private String targetEntity;
        private List<AuditDetail> details;
    }
}