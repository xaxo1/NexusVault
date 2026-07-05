package com.nexusvault.msadmin.repository;

import com.nexusvault.msadmin.model.AuditDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la entidad {@link AuditDetail}.
 * Proporciona métodos para acceder y gestionar los detalles de auditoría en la base de datos.
 */
@Repository
public interface AuditDetailRepository extends JpaRepository<AuditDetail, Long> {
    
    /**
     * Busca los detalles de auditoría filtrando por el nombre del campo modificado.
     *
     * @param fieldName el nombre del campo alterado.
     * @return una lista de detalles de auditoría asociados al campo.
     */
    // Buscar todas las veces que se alteró un campo específico
    List<AuditDetail> findByFieldName(String fieldName);
}