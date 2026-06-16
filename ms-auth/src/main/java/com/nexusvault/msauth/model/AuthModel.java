package com.nexusvault.msauth.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

//-------1-acá comienza con el schema general del modelo
@Schema(description = "Entidad de base de datos que almacena las credenciales y estados de autenticación")
@Entity
@Table(name = "auth_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthModel {

    //------2-acá comienza con el id
    @Schema(description = "Identificador único incremental del registro de seguridad", example = "105")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Dirección de correo electrónico (llave de login)", example = "usuario@nexusvault.com")
    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Schema(description = "Hash seguro BCrypt de la contraseña", example = "$2a$10$e0MYzXyK...")
    @Column(nullable = false)
    private String password;

    @Schema(description = "Rol asignado para el control de accesos", example = "MODERATOR")
    @Column(nullable = false, length = 20)
    private String role;

    @Schema(description = "Fecha y hora de registro del usuario", example = "2026-03-31T12:30:00")
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Schema(description = "Define si el usuario está facultado para ingresar al sistema", example = "true")
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.isActive == null) {
            this.isActive = true;
        }
    }
}