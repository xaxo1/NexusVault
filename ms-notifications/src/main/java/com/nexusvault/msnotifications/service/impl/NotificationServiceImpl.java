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

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationsRepository notificationsRepository;

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

    @Override
    @Transactional(readOnly = true)
    public List<ModelNotifications> getAllNotifications() {
        return notificationsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ModelNotifications> getNotificationsByStatus(NotificationStatus status) {
        return notificationsRepository.findByStatus(status);
    }
}