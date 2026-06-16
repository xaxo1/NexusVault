package com.nexusvault.msnotifications.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "Estructura de payload requerida por los microservicios para solicitar la emisión de una alerta")
@Data
public class NotificationRequestDTO {

    @Schema(description = "Identificador numérico del usuario destinatario", example = "5")
    @NotNull(message = "El ID de usuario es obligatorio")
    private Long userId;

    @Schema(description = "Dirección de correo electrónico donde se despachará el mensaje", example = "usuario5@correo.com")
    @NotBlank(message = "El correo de destino es obligatorio")
    @Email(message = "El formato del correo es inválido")
    private String targetEmail;

    @Schema(description = "Encabezado o asunto principal de la notificación", example = "¡Tu orden ha sido confirmada!")
    @NotBlank(message = "El título no puede estar vacío")
    private String title;

    @Schema(description = "Contenido extendido o cuerpo del mensaje de la alerta", example = "Hola, te informamos que los ítems adquiridos ya se encuentran disponibles en tu inventario.")
    @NotBlank(message = "El cuerpo del mensaje es obligatorio")
    private String message;
}