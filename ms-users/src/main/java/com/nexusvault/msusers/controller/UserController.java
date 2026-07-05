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

/**
 * Controlador REST que expone las operaciones relacionadas con la gestión de perfiles de usuario.
 */
@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
//1-acá el tag general de la rúbrica
@Tag(name = "Usuarios", description = "Endpoints para la administración integral de perfiles de jugador, reputación y avatares")
public class UserController {

    private final UserService userService;

    /**
     * Recupera la lista completa de todos los perfiles de usuario registrados en el sistema.
     *
     * @return Una respuesta HTTP que contiene la lista de entidades {@link UserModel}.
     */
    @GetMapping("/profiles")
    @Operation(summary = "Listar todos los perfiles", description = "Recupera una lista completa con todos los perfiles de usuario registrados en la base de datos de Nexus Vault.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado de perfiles recuperado con éxito")
    })
    public ResponseEntity<List<UserModel>> getAllProfiles() {
        log.info("Petición REST recibida: Obtener todos los perfiles");
        return ResponseEntity.ok(userService.obtenerTodosLosPerfiles());
    }

    /**
     * Busca y retorna un perfil de usuario utilizando su identificador de autenticación de ms-auth.
     *
     * @param id El identificador único del usuario en el sistema de autenticación (authId).
     * @return Una respuesta HTTP con el perfil del usuario, o 404 si no se encuentra.
     */
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

    /**
     * Crea un nuevo perfil físico en la base de datos asociado a un usuario.
     *
     * @param userModel El objeto {@link UserModel} que contiene los datos del nuevo perfil a crear.
     * @return Una respuesta HTTP con el perfil recién creado y un estado de CREATED (201).
     */
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

    /**
     * Actualiza la información modificable de un perfil de usuario existente.
     *
     * @param id El identificador incremental del perfil que se va a actualizar.
     * @param userModel El objeto con los nuevos datos a aplicar (ej. nickname, URL de avatar).
     * @return Una respuesta HTTP con el perfil actualizado, o 404 si el perfil especificado no existe.
     */
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

    /**
     * Elimina permanentemente un perfil de usuario del sistema.
     *
     * @param id El identificador del perfil de usuario que se desea eliminar.
     * @return Una respuesta HTTP 204 (No Content) indicando que la eliminación fue completada con éxito.
     */
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