package com.nexusvault.msadmin.service;

import com.nexusvault.msadmin.model.AuditDetail;
import com.nexusvault.msadmin.model.AuditLog;
import com.nexusvault.msadmin.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio encargado de gestionar la lógica de negocio relacionada con la auditoría.
 * Proporciona métodos para registrar y consultar eventos de auditoría.
 */
@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    /**
     * Crea un nuevo registro de auditoría junto con sus detalles asociados.
     *
     * @param adminId el identificador del administrador que realiza la acción.
     * @param action el nombre de la acción realizada (ej. UPDATE, DELETE).
     * @param targetEntity la entidad sobre la que recae la acción.
     * @param details una lista de detalles atómicos (campos modificados).
     * @return el registro de auditoría guardado.
     */
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

    /**
     * Obtiene todos los registros de auditoría realizados por un administrador específico.
     *
     * @param adminId el identificador del administrador.
     * @return una lista de registros de auditoría asociados.
     */
    @Transactional(readOnly = true)
    public List<AuditLog> getLogsByAdmin(Long adminId) {
        return auditLogRepository.findByAdminId(adminId);
    }

    /**
     * Obtiene todos los registros de auditoría realizados sobre una entidad específica.
     *
     * @param targetEntity el nombre de la entidad modificada.
     * @return una lista de registros de auditoría asociados a la entidad.
     */
    @Transactional(readOnly = true)
    public List<AuditLog> getLogsByEntity(String targetEntity) {
        return auditLogRepository.findByTargetEntity(targetEntity);
    }
}
