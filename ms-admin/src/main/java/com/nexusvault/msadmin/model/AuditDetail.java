package com.nexusvault.msadmin.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

//-------1-acá comienza con el schema general del modelo
/**
 * Entidad que representa el detalle de un registro de auditoría.
 * Registra los cambios específicos de los atributos (valor anterior y nuevo valor).
 */
@Schema(description = "Detalle atómico que registra los cambios sufridos en un campo particular")
@Entity
@Table(name = "audit_details")
@Data
@NoArgsConstructor
public class AuditDetail {

    //------2-acá comienza con el id
    @Schema(description = "ID único de la traza de detalle", example = "98551")
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    @JsonIgnore 
    @ManyToOne 
    @JoinColumn(name = "audit_log_id")
    private AuditLog auditLog;

    @Schema(description = "Nombre del atributo afectado", example = "role")
    private String fieldName;  

    @Schema(description = "Valor inicial pre-operación", example = "MODERATOR")
    private String oldValue;   

    @Schema(description = "Nuevo valor almacenado", example = "SUPER_ADMIN")
    private String newValue;   

    public AuditDetail(String fieldName, String oldValue, String newValue, AuditLog auditLog) {
        this.fieldName = fieldName;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.auditLog = auditLog;
    }
}