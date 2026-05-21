package com.nexusvault.msauth.controller;

import com.nexusvault.msauth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // Endpoint simulado de Login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {

        boolean isAuthenticated = authService.authenticateUser(email, password);

        if (isAuthenticated) {
            return ResponseEntity.ok("¡Bienvenido a Nexus Vault!");
        } else {
            return ResponseEntity.status(401).body("Credenciales incorrectas o cuenta inactiva");
        }
    }
}