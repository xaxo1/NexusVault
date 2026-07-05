package com.nexusvault.msreports.repository;

import com.nexusvault.msreports.model.ModelReports;
import com.nexusvault.msreports.model.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la gestión de las entidades {@link ModelReports} en la base de datos.
 * Proporciona métodos para operaciones de tipo CRUD y consultas personalizadas sobre los reportes.
 */
@Repository
public interface ReportsRepository extends JpaRepository<ModelReports, Long> {
    /**
     * Busca y retorna una lista de reportes asociados a un tipo específico.
     *
     * @param tipoReporte El tipo de reporte a buscar (ej: VENTAS_MENSUALES).
     * @return Una lista de entidades {@link ModelReports} que coinciden con el tipo proporcionado.
     */
    // Buscar reportes por tipo (ej: buscar todos los de VENTAS_MENSUALES)
    List<ModelReports> findByTipoReporte(ReportType tipoReporte);
}