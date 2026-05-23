package com.nexusvault.msnotifications.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NotificationRequestDTO {

    @NotNull(message = "El ID de usuario es obligatorio")
    private Long userId;

    @NotBlank(message = "El correo de destino es obligatorio")
    @Email(message = "El formato del correo es inválido")
    private String targetEmail;

    @NotBlank(message = "El título no puede estar vacío")
    private String title;

    @NotBlank(message = "El cuerpo del mensaje es obligatorio")
    private String message;
}