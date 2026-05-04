package com.nexusvault.msnotifications.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ModelNotifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * [DESTINATARIO]
     * A quién va dirigida la notificación. Puente con MS-Auth/MS-Users.
     * No es único, un usuario recibe miles de avisos en su vida.
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /*
     * [CONTACTO DIRECTO]
     * Guardamos el correo aquí temporalmente para no tener que estar
     * preguntándole al MS-Auth a cada rato cuál era el email del usuario 5.
     */
    @Column(name = "target_email", nullable = false, length = 100)
    private String targetEmail;

    @Column(nullable = false, length = 150)
    private String title;

    /*
     * 'length = 1000' porque el cuerpo de un correo o mensaje puede ser largo.
     */
    @Column(nullable = false, length = 1000)
    private String message;

    /*
     * [CONTROL DE ESTADO - LA MEJORA DEL DISEÑO]
     * Usamos un Enum en formato String para saber si ya se envió.
     * Valores posibles: "PENDING", "SENT", "FAILED"
     */
    @Column(nullable = false, length = 20)
    private String status;

    /*
     * Trazabilidad de cuándo se creó el aviso y cuándo finalmente salió del servidor.
     */
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    /*
     * [TRIGGER DE INICIALIZACIÓN]
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();

        // ¡La regla de negocio clave! Toda notificación nace pendiente de envío.
        if (this.status == null) {
            this.status = "PENDING";
        }
    }

}
