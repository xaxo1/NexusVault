package com.nexusvault.msadmin.service;

import com.nexusvault.msadmin.model.AuditDetail;
import com.nexusvault.msadmin.model.AuditLog;
import com.nexusvault.msadmin.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    @Transactional
    public AuditLog createAuditRecord(Long adminId, String action, String targetEntity, List<AuditDetail> details) {
        AuditLog auditLog = new AuditLog();
        auditLog.setAdminId(adminId);
        auditLog.setAction(action);
        auditLog.setTargetEntity(targetEntity);

        // Si hay detalles técnicos (cambios de campos), los vinculamos al Log principal
        if (details != null && !details.isEmpty()) {
            details.forEach(detail -> detail.setAuditLog(auditLog));
            auditLog.setDetails(details);
        }

        // Al guardar el auditLog, gracias a CascadeType.ALL, se guardarán los AuditDetail automáticamente
        return auditLogRepository.save(auditLog);
    }

    @Transactional(readOnly = true)
    public List<AuditLog> getLogsByAdmin(Long adminId) {
        return auditLogRepository.findByAdminId(adminId);
    }

    @Transactional(readOnly = true)
    public List<AuditLog> getLogsByEntity(String targetEntity) {
        return auditLogRepository.findByTargetEntity(targetEntity);
    }
}
