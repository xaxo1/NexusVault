package com.nexusvault.msadmin.repository;

import com.nexusvault.msadmin.model.AuditDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditDetailRepository extends JpaRepository<AuditDetail, Long> {
    
    // Buscar todas las veces que se alteró un campo específico
    List<AuditDetail> findByFieldName(String fieldName);
}