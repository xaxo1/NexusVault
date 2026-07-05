package com.nexusvault.mswallet.dto;

/**
 * DTO que empaqueta los parámetros requeridos para el envío de notificaciones mediante microservicios.
 *
 * @param userId El identificador único del usuario notificado.
 * @param targetEmail El correo electrónico del destinatario.
 * @param title El asunto o título central del aviso.
 * @param message El contenido textual descriptivo de la notificación.
 */
public record NotificationRequestDTO(
    Long userId,
    String targetEmail,
    String title,
    String message
) {}