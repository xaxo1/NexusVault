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

/**
 * Controlador REST encargado de gestionar los registros de auditoría.
 * Permite registrar y consultar acciones realizadas por los administradores.
 */
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
    /**
     * Registra un nuevo evento de auditoría en el sistema.
     *
     * @param request los datos necesarios para crear el registro de auditoría.
     * @return una respuesta con el log de auditoría creado y el estado HTTP 201 (CREATED).
     */
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
    /**
     * Obtiene todos los registros de auditoría asociados a un administrador específico.
     *
     * @param adminId el identificador del administrador.
     * @return una lista de logs de auditoría correspondientes al administrador.
     */
    @GetMapping("/admin/{adminId}")
    public ResponseEntity<List<AuditLog>> getLogsByAdmin(@PathVariable Long adminId) {
        List<AuditLog> logs = auditService.getLogsByAdmin(adminId);
        return ResponseEntity.ok(logs);
    }

    @Operation(summary = "Obtener logs por Entidad Objetivo", description = "Permite auditar los cambios sufridos en un módulo específico del sistema (ej: PRODUCT, USER).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista filtrada por entidad de destino recuperada")
    })
    /**
     * Obtiene todos los registros de auditoría asociados a una entidad objetivo.
     *
     * @param targetEntity el nombre de la entidad objetivo (ej. PRODUCT, USER).
     * @return una lista de logs de auditoría correspondientes a la entidad.
     */
    @GetMapping("/entity/{targetEntity}")
    public ResponseEntity<List<AuditLog>> getLogsByEntity(@PathVariable String targetEntity) {
        List<AuditLog> logs = auditService.getLogsByEntity(targetEntity);
        return ResponseEntity.ok(logs);
    }

    /**
     * DTO utilizado para inicializar un registro de auditoría.
     */
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