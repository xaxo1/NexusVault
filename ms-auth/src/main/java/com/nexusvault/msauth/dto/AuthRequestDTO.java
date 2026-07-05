package com.nexusvault.msauth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Objeto de Transferencia de Datos (DTO) que representa una solicitud de autenticación.
 * Contiene las credenciales necesarias para que un usuario inicie sesión.
 */
@Schema(description = "Estructura de datos requerida para solicitar acceso al sistema")
@Data
public class AuthRequestDTO {

    @Schema(description = "Correo electrónico registrado", example = "usuario@nexusvault.com")
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El formato del email es inválido")
    private String email;

    @Schema(description = "Contraseña asociada a la cuenta", example = "Password123!")
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}