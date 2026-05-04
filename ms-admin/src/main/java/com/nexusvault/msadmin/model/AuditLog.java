package com.nexusvault.msadmin.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "audit_logs")
@Data
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "admin_id")
    private Long adminId; 

    private String action; 
    
    @Column(name = "target_entity")
    private String targetEntity; // Ej: "PRODUCT", "USER"

    @Column(updatable = false)
    private LocalDateTime timestamp;

    // Relación 1 a N: Un log tiene muchos detalles técnicos
    @OneToMany(mappedBy = "auditLog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AuditDetail> details;

    @PrePersist
    protected void onAudit() {
        this.timestamp = LocalDateTime.now();
    }
}