package com.nexusvault.msadmin.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

//-------1-acá comienza con el schema general del modelo
@Schema(description = "Esquema maestro que centraliza la cabecera de una acción de auditoría")
@Entity
@Table(name = "audit_logs")
@Getter
@Setter
@NoArgsConstructor
public class AuditLog {

    //------2-acá comienza con el id
    @Schema(description = "ID del log de auditoría", example = "2401")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "ID del administrador que ejecutó la acción", example = "1")
    @Column(name = "admin_id")
    private Long adminId; 

    @Schema(description = "Identificador de la acción realizada", example = "DELETE_USER")
    private String action; 
    
    @Schema(description = "Entidad del ecosistema que fue modificada", example = "USER_PROFILE")
    @Column(name = "target_entity")
    private String targetEntity;

    @Schema(description = "Fecha exacta UTC del procesamiento", example = "2026-03-31T15:00:00")
    @Column(updatable = false)
    private LocalDateTime timestamp;


    @Schema(description = "Colección de cambios atómicos asociados a este log")
    @OneToMany(mappedBy = "auditLog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AuditDetail> details;

    @PrePersist
    protected void onAudit() {
        this.timestamp = LocalDateTime.now();
    }
}