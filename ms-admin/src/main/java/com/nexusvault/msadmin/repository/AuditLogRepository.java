package com.nexusvault.msadmin.repository;

import com.nexusvault.msadmin.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    // ¿Qué hizo el Admin X?
    List<AuditLog> findByAdminId(Long adminId);

    // ¿Qué ha pasado con la entidad "PRODUCT" o "USER"?
    List<AuditLog> findByTargetEntity(String targetEntity);
}