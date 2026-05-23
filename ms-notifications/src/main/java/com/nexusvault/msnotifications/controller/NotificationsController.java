package com.nexusvault.msnotifications.controller;

import com.nexusvault.msnotifications.dto.NotificationRequestDTO;
import com.nexusvault.msnotifications.model.ModelNotifications;
import com.nexusvault.msnotifications.model.NotificationStatus;
import com.nexusvault.msnotifications.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationsController {

    private final NotificationService notificationService;

    // Endpoint clave para que otros microservicios gatillen notificaciones
    @PostMapping("/send")
    public ResponseEntity<ModelNotifications> requestNotification(@Valid @RequestBody NotificationRequestDTO requestDTO) {
        ModelNotifications created = notificationService.createNotification(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/logs")
    public ResponseEntity<List<ModelNotifications>> getAllLogs() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @GetMapping("/pending")
    public ResponseEntity<List<ModelNotifications>> getPending() {
        return ResponseEntity.ok(notificationService.getNotificationsByStatus(NotificationStatus.PENDING));
    }

    @PatchMapping("/{id}/confirm")
    public ResponseEntity<ModelNotifications> confirmDelivery(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.markAsSent(id));
    }
}