package com.nexusvault.msusers.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

//-------1-acá comienza con el schema general del modelo
@Schema(description = "Entidad representativa del perfil público y estadísticas comunitarias de un jugador")
@Entity
@Table(name = "user_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModel {

    //------2-acá comienza con el id
    @Schema(description = "ID primario, incremental y autogenerado del perfil", example = "3001")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "ID del usuario asociado en la base de seguridad ms-auth (Relación 1:1 distribuida)", example = "105")
    @NotNull(message = "El Auth ID es obligatorio")
    @Column(name = "auth_id", unique = true, nullable = false)
    private Long authId;

    @Schema(description = "Nombre en pantalla único elegido por el jugador", example = "NexusKnight_99")
    @NotBlank(message = "El nickname no puede estar vacío")
    @Column(unique = true, nullable = false, length = 50)
    private String nickname;

    @Schema(description = "Nivel de reputación del usuario en la plataforma (Inicializa en 0)", example = "150")
    @Column(nullable = false)
    private Integer reputacion;

    @Schema(description = "Fecha y hora exacta en la que se dio de alta el perfil", example = "2026-06-16T15:30:00")
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Schema(description = "Flag indicador del estado de la cuenta", example = "true")
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Schema(description = "Ruta o URL del recurso de imagen para el avatar del jugador", example = "https://nexusvault.com/storage/avatars/user3001.png")
    @Column(name = "avatar_url")
    private String avatarUrl;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.reputacion == null) {
            this.reputacion = 0;
        }
        if (this.isActive == null) {
            this.isActive = true;
        }
    }
}