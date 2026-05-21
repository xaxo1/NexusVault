package com.nexusvault.msadmin.controller;

import com.nexusvault.msadmin.model.AuditDetail;
import com.nexusvault.msadmin.model.AuditLog;
import com.nexusvault.msadmin.service.AuditService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/audits")
@RequiredArgsConstructor
public class AuditController {

    private final AuditService auditService;

    // POST: /api/v1/audits
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

    // GET: /api/v1/audits/admin/{adminId}
    @GetMapping("/admin/{adminId}")
    public ResponseEntity<List<AuditLog>> getLogsByAdmin(@PathVariable Long adminId) {
        List<AuditLog> logs = auditService.getLogsByAdmin(adminId);
        return ResponseEntity.ok(logs);
    }

    // GET: /api/v1/audits/entity/{targetEntity}
    @GetMapping("/entity/{targetEntity}")
    public ResponseEntity<List<AuditLog>> getLogsByEntity(@PathVariable String targetEntity) {
        List<AuditLog> logs = auditService.getLogsByEntity(targetEntity);
        return ResponseEntity.ok(logs);
    }

    // --- DTO interno para recibir el JSON de creación ---
    @Data
    public static class AuditCreateRequest {
        private Long adminId;
        private String action;
        private String targetEntity;
        private List<AuditDetail> details;
    }
}