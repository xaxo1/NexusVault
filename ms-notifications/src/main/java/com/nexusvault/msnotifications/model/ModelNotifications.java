package com.nexusvault.msnotifications.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

//-------1-acá comienza con el schema general del modelo
@Schema(description = "Entidad que registra la auditoría, trazabilidad y estado de cada notificación emitida")
@Entity
@Table(name = "notifications_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModelNotifications {

    //------2-acá comienza con el id
    @Schema(description = "Clave primaria autoincremental de la bitácora de auditoría", example = "501")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "ID del usuario asociado que recibe la alerta", example = "5")
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Schema(description = "Copia del correo del destinatario al momento del registro", example = "usuario5@correo.com")
    @Column(name = "target_email", nullable = false, length = 100)
    private String targetEmail;

    @Schema(description = "Título o asunto resumido del aviso", example = "Alerta de Inicio de Sesión")
    @Column(nullable = false, length = 150)
    private String title;

    @Schema(description = "Cuerpo completo de texto que compone el mensaje enviado", example = "Se ha detectado un inicio de sesión desde una nueva dirección IP.")
    @Column(nullable = false, length = 1000)
    private String message;

    @Schema(description = "Estado transaccional en el que se encuentra la notificación", example = "PENDING")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NotificationStatus status;

    @Schema(description = "Fecha y hora exacta en la que se grabó la solicitud en el sistema", example = "2026-03-31T18:00:00")
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Schema(description = "Fecha y hora en la que los servicios externos confirmaron la salida del mensaje", example = "2026-03-31T18:02:15")
    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = NotificationStatus.PENDING;
        }
    }
}