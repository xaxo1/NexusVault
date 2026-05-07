package com.nexusvault.msreports.repository;

import com.nexusvault.msreports.model.ModelReports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportsRepository extends JpaRepository<ModelReports, Long> {
    // Buscar reportes por tipo (ej: buscar todos los de VENTAS_MENSUALES)
    List<ModelReports> findByTipoReporte(String tipoReporte);
}