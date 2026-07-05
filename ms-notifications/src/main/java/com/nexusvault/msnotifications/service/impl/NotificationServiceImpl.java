package com.nexusvault.msnotifications.service.impl;

import com.nexusvault.msnotifications.dto.NotificationRequestDTO;
import com.nexusvault.msnotifications.model.ModelNotifications;
import com.nexusvault.msnotifications.model.NotificationStatus;
import com.nexusvault.msnotifications.repository.NotificationsRepository;
import com.nexusvault.msnotifications.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementación de la lógica de negocio para la gestión de notificaciones.
 * Proporciona métodos para registrar nuevas alertas, confirmar su despacho y consultarlas.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationsRepository notificationsRepository;

    /**
     * Crea y registra una nueva notificación con estado inicial PENDING en base a la información provista.
     *
     * @param request Objeto que contiene los datos a enviar como título, mensaje y destinatario.
     * @return La entidad de notificación guardada en la base de datos.
     */
    @Override
    @Transactional
    public ModelNotifications createNotification(NotificationRequestDTO request) {
        log.info("Registrando nueva notificación pendiente para el usuario ID: {}", request.getUserId());
        
        ModelNotifications notification = ModelNotifications.builder()
                .userId(request.getUserId())
                .targetEmail(request.getTargetEmail())
                .title(request.getTitle())
                .message(request.getMessage())
                .status(NotificationStatus.PENDING)
                .build();

        return notificationsRepository.save(notification);
    }

    /**
     * Cambia el estado de una notificación a SENT (enviada) y estampa la fecha de envío actual.
     *
     * @param id Identificador de la notificación a modificar.
     * @return El registro de la notificación ya actualizada.
     * @throws IllegalArgumentException si no se encuentra la notificación con el ID proporcionado.
     */
    @Override
    @Transactional
    public ModelNotifications markAsSent(Long id) {
        log.info("Cambiando estado de notificación ID: {} a SENT", id);
        ModelNotifications notification = notificationsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notificación no encontrada con ID: " + id));
        
        notification.setStatus(NotificationStatus.SENT);
        notification.setSentAt(LocalDateTime.now());
        return notificationsRepository.save(notification);
    }

    /**
     * Retorna el histórico total de notificaciones, sin aplicar filtros.
     *
     * @return Lista con todos los registros de notificaciones.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ModelNotifications> getAllNotifications() {
        return notificationsRepository.findAll();
    }

    /**
     * Devuelve las notificaciones filtradas según el estado solicitado.
     *
     * @param status Estado específico por el cual se desea filtrar.
     * @return Lista de notificaciones que coinciden con el estado.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ModelNotifications> getNotificationsByStatus(NotificationStatus status) {
        return notificationsRepository.findByStatus(status);
    }
}