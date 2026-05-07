package com.nexusvault.msnotifications.controller;

import com.nexusvault.msnotifications.model.ModelNotifications;
import com.nexusvault.msnotifications.repository.NotificationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/notifications")

public class NotificationsController {

    @Autowired
    private NotificationsRepository notificationsRepository;

    @GetMapping("/logs")
    public List<ModelNotifications> getAllLogs() {
        return notificationsRepository.findAll();
    }

    @GetMapping("/pending")
    public List<ModelNotifications> getPending() {
        return notificationsRepository.findByStatus("PENDING");
    }

}
