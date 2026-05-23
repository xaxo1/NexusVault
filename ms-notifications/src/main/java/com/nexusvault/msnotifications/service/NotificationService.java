package com.nexusvault.msnotifications.service;

import com.nexusvault.msnotifications.dto.NotificationRequestDTO;
import com.nexusvault.msnotifications.model.ModelNotifications;
import com.nexusvault.msnotifications.model.NotificationStatus;

import java.util.List;

public interface NotificationService {
    ModelNotifications createNotification(NotificationRequestDTO request);
    ModelNotifications markAsSent(Long id);
    List<ModelNotifications> getAllNotifications();
    List<ModelNotifications> getNotificationsByStatus(NotificationStatus status);
}