package com.nexusvault.msusers.controller;

import com.nexusvault.msusers.model.UserModel;
import com.nexusvault.msusers.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
//1-acá el tag general de la rúbrica
@Tag(name = "Usuarios", description = "Endpoints para la administración integral de perfiles de jugador, reputación y avatares")
public class UserController {

    private final UserService userService;

    @GetMapping("/profiles")
    @Operation(summary = "Listar todos los perfiles", description = "Recupera una lista completa con todos los perfiles de usuario registrados en la base de datos de Nexus Vault.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado de perfiles recuperado con éxito")
    })
    public ResponseEntity<List<UserModel>> getAllProfiles() {
        log.info("Petición REST recibida: Obtener todos los perfiles");
        return ResponseEntity.ok(userService.obtenerTodosLosPerfiles());
    }

    @GetMapping("/profiles/auth/{id}")
    @Operation(summary = "Buscar perfil por Auth ID", description = "Permite consultar un perfil de usuario utilizando el identificador único del microservicio remoto ms-auth.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Perfil de usuario localizado correctamente"),
        @ApiResponse(responseCode = "404", description = "No existe un perfil vinculado a ese Auth ID", content = @Content)
    })
    public ResponseEntity<UserModel> getProfileById(@PathVariable Long id) {
        log.info("Petición REST recibida: Obtener perfil con authId: {}", id);
        return userService.obtenerPorAuthId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/profiles")
    @Operation(summary = "Crear nuevo perfil de usuario", description = "Registra un nuevo perfil físico asociado a un usuario, inicializando su reputación en 0 por regla de negocio.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Perfil creado y guardado exitosamente"),
        @ApiResponse(responseCode = "400", description = "El payload contiene campos inválidos o vacíos", content = @Content)
    })
    public ResponseEntity<UserModel> createProfile(@Valid @RequestBody UserModel userModel) {
        log.info("Petición REST recibida: Crear nuevo perfil");
        UserModel createdUser = userService.crearPerfil(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/profiles/{id}")
    @Operation(summary = "Actualizar perfil existente", description = "Modifica atributos editables como el nickname y la URL del avatar de un perfil basándose en su ID incremental.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Perfil actualizado de forma correcta"),
        @ApiResponse(responseCode = "400", description = "Datos provistos inválidos", content = @Content),
        @ApiResponse(responseCode = "404", description = "No se encontró el perfil con el ID especificado", content = @Content)
    })
    public ResponseEntity<UserModel> updateProfile(@PathVariable Long id, @Valid @RequestBody UserModel userModel) {
        log.info("Petición REST recibida: Actualizar perfil ID: {}", id);
        return userService.actualizarPerfil(id, userModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/profiles/{id}")
    @Operation(summary = "Eliminar un perfil de usuario", description = "Remueve físicamente el registro del perfil de la base de datos por medio de su identificador primario.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Perfil eliminado correctamente del sistema"),
        @ApiResponse(responseCode = "404", description = "El perfil a eliminar no existe", content = @Content)
    })
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        log.info("Petición REST recibida: Eliminar perfil ID: {}", id);
        userService.eliminarPerfil(id);
        return ResponseEntity.noContent().build();
    }
}