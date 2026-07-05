package com.nexusvault.msnotifications.service;

import com.nexusvault.msnotifications.dto.NotificationRequestDTO;
import com.nexusvault.msnotifications.model.ModelNotifications;
import com.nexusvault.msnotifications.model.NotificationStatus;

import java.util.List;

/**
 * Interfaz para el servicio central de notificaciones.
 * Define la lógica y operaciones necesarias para crear, consultar y actualizar alertas del sistema.
 */
public interface NotificationService {
    /**
     * Crea un nuevo registro de notificación a partir de la petición.
     *
     * @param request Datos del destinatario y contenido de la alerta.
     * @return El modelo de la notificación creado y persistido.
     */
    ModelNotifications createNotification(NotificationRequestDTO request);

    /**
     * Actualiza el estado de una notificación específica a enviada.
     *
     * @param id Identificador de la notificación a marcar como enviada.
     * @return La notificación con su estado actualizado.
     */
    ModelNotifications markAsSent(Long id);

    /**
     * Recupera todas las notificaciones registradas históricamente en el sistema.
     *
     * @return Lista general de notificaciones.
     */
    List<ModelNotifications> getAllNotifications();

    /**
     * Recupera una lista de notificaciones cuyo estado coincida con el provisto.
     *
     * @param status Estado a buscar (ej. PENDING, SENT).
     * @return Lista de notificaciones con dicho estado.
     */
    List<ModelNotifications> getNotificationsByStatus(NotificationStatus status);
}