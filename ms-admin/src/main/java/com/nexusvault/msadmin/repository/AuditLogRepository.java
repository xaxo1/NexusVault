package com.nexusvault.msadmin.repository;

import com.nexusvault.msadmin.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la entidad {@link AuditLog}.
 * Proporciona métodos para acceder y gestionar los registros de auditoría principales en la base de datos.
 */
@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    /**
     * Busca los registros de auditoría generados por un administrador específico.
     *
     * @param adminId el identificador del administrador.
     * @return una lista de registros de auditoría asociados al administrador.
     */
    // ¿Qué hizo el Admin X?
    List<AuditLog> findByAdminId(Long adminId);

    /**
     * Busca los registros de auditoría asociados a una entidad específica del sistema.
     *
     * @param targetEntity el nombre de la entidad (ej. PRODUCT, USER).
     * @return una lista de registros de auditoría correspondientes a la entidad.
     */
    // ¿Qué ha pasado con la entidad "PRODUCT" o "USER"?
    List<AuditLog> findByTargetEntity(String targetEntity);
}