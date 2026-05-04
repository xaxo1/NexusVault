package com.nexusvault.msusers.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModelUser {


    //
    //EL entity y table convierten esta clase Java en una tabla real de MySQL llamada user_profiles
    //
    //Usamos Integer aqui y no int porque int nunca puede ser nulo y si este no se le asigna un valor, automaticamente deja en c0, en cambio el Integer no, este si puede ser null
    //

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Este campo vincula este perfil con las credenciales en MS-Auth
    @Column(name = "auth_id", unique = true, nullable = false)
    private Long authId;

    // Atributos específicos del diagrama UML que hicimos en ing en software (Clase Jugador)
    @Column(unique = true, nullable = false, length = 50)
    private String nickname;

    @Column(nullable = false)
    private Integer reputacion;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now(); // Automaticamente le asignamos la fecha de creacion de la cuenta
        // Por regla de negocio, un jugador nuevo siempre empieza con 0 de reputación
        if (this.reputacion == null) { // Si la cuenta es nueva, su reputacion estaria en null entonces le asignamos 0
            this.reputacion = 0;
        }
        if (this.isActive == null) { // Si la cuenta es nueva, automaticamente se la dejamos activa
            this.isActive = true;
        }
    }
}
