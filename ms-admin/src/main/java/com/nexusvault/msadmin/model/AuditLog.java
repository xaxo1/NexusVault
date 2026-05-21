package com.nexusvault.msadmin.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "audit_logs")
@Getter
@Setter
@NoArgsConstructor
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "admin_id")
    private Long adminId; 

    private String action; 
    
    @Column(name = "target_entity")
    private String targetEntity;

    @Column(updatable = false)
    private LocalDateTime timestamp;

    @OneToMany(mappedBy = "auditLog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AuditDetail> details;

    @PrePersist
    protected void onAudit() {
        this.timestamp = LocalDateTime.now();
    }
}