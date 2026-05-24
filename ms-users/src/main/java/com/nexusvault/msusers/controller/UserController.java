package com.nexusvault.msusers.controller;

import com.nexusvault.msusers.model.UserModel;
import com.nexusvault.msusers.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profiles")
    public ResponseEntity<List<UserModel>> getAllProfiles() {
        return ResponseEntity.ok(userService.obtenerTodosLosPerfiles());
    }

    @GetMapping("/profiles/auth/{id}")
    public ResponseEntity<UserModel> getProfileById(@org.springframework.web.bind.annotation.PathVariable Long id) {
        // Implementación básica para obtener el perfil o NotFound.
        // asumiendo que obtenerTodosLosPerfiles devuelve todo, filtramos. (Lo ideal sería un método en userService)
        return userService.obtenerTodosLosPerfiles().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}