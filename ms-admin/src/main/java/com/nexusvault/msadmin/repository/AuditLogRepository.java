package com.nexusvault.msadmin.repository;

import com.nexusvault.msadmin.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    // Método extra para ver todo el historial de acciones de un administrador específico
    List<AuditLog> findByAdminId(Long adminId);
    
}