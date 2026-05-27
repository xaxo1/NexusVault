package com.nexusvault.msusers.controller;

import com.nexusvault.msusers.model.UserModel;
import com.nexusvault.msusers.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j // Agregamos Slf4j para la rúbrica
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 1. Obtener todos
    @GetMapping("/profiles")
    public ResponseEntity<List<UserModel>> getAllProfiles() {
        log.info("Petición REST recibida: Obtener todos los perfiles");
        return ResponseEntity.ok(userService.obtenerTodosLosPerfiles());
    }

    // 2. Obtener por Auth ID
    @GetMapping("/profiles/auth/{id}")
    public ResponseEntity<UserModel> getProfileById(@PathVariable Long id) {
        log.info("Petición REST recibida: Obtener perfil con authId: {}", id);
        return userService.obtenerPorAuthId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. Crear nuevo perfil (POST) - Agregado para cumplir CRUD
    @PostMapping("/profiles")
    public ResponseEntity<UserModel> createProfile(@Valid @RequestBody UserModel userModel) {
        log.info("Petición REST recibida: Crear nuevo perfil");
        UserModel createdUser = userService.crearPerfil(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    // 4. Actualizar perfil existente (PUT) - Agregado para cumplir CRUD
    @PutMapping("/profiles/{id}")
    public ResponseEntity<UserModel> updateProfile(@PathVariable Long id, @Valid @RequestBody UserModel userModel) {
        log.info("Petición REST recibida: Actualizar perfil ID: {}", id);
        return userService.actualizarPerfil(id, userModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 5. Eliminar perfil (DELETE) - Agregado para cumplir CRUD
    @DeleteMapping("/profiles/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        log.info("Petición REST recibida: Eliminar perfil ID: {}", id);
        userService.eliminarPerfil(id);
        return ResponseEntity.noContent().build();
    }
}