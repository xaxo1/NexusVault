package com.nexusvault.msadmin.controller;

import com.nexusvault.msadmin.model.Admin;
import com.nexusvault.msadmin.model.AuditLog;
import com.nexusvault.msadmin.repository.AdminRepository;
import com.nexusvault.msadmin.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List; 

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository; 

    @Autowired
    private AuditLogRepository auditLogRepository;

    @GetMapping("/users")
    public List<Admin> getAllAdmins() { 
        return adminRepository.findAll(); 
    }

    @GetMapping("/logs")
    public List<AuditLog> getAuditLogs() {
        return auditLogRepository.findAll(); // Esto trae los logs con sus detalles
    }
}