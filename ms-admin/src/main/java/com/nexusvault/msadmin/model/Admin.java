package com.nexusvault.msadmin.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

//-------1-acá comienza con el schema general del modelo
@Schema(description = "Entidad estructural que representa a un usuario con privilegios de Administrador")
@Entity
@Table(name = "admins")
@Data
public class Admin {

    //------2-acá comienza con el id
    @Schema(description = "ID numérico único auto-incremental", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nombre completo o alias corporativo", example = "Carlos Mendoza")
    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @Schema(description = "Dirección única de correo electrónico", example = "carlos.admin@nexusvault.com")
    @Email(message = "Email inválido")
    @NotBlank(message = "El email es obligatorio")
    @Column(unique = true)
    private String email;

    @Schema(description = "Hash secreto de credenciales para el inicio de sesión", example = "hash_encrypted_string")
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Schema(description = "Rol administrativo de permisos dentro del ecosistema", example = "SUPER_ADMIN")
    private String role; 
    
    @Schema(description = "Informa si el usuario se encuentra vigente en sus facultades", example = "true")
    private boolean active = true;
}