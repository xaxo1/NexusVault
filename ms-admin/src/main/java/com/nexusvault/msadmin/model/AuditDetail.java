package com.nexusvault.msadmin.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name = "audit_details")
@Data
@NoArgsConstructor
public class AuditDetail {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID único para cada detalle técnico

    @JsonIgnore // Para evitar ciclos infinitos al serializar
    @ManyToOne // Muchos detalles pertenecen a un log
    @JoinColumn(name = "audit_log_id")
    private AuditLog auditLog;

    private String fieldName;  // Ej: "precio"
    private String oldValue;   // Ej: "100"
    private String newValue;   // Ej: "80"

    public AuditDetail(String fieldName, String oldValue, String newValue, AuditLog auditLog) {
        this.fieldName = fieldName;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.auditLog = auditLog;
    }
}