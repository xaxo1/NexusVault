package com.nexusvault.msadmin.controller;

import com.nexusvault.msadmin.model.Admin;
import com.nexusvault.msadmin.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar operaciones relacionadas con los administradores.
 * Proporciona endpoints para crear, leer, actualizar y desactivar/eliminar administradores.
 */
@RestController
@RequestMapping("/api/v1/admins")
@RequiredArgsConstructor
//1-acá el tag general
@Tag(name = "Administradores", description = "Endpoints para la gestión y administración de usuarios administradores en Nexus Vault")
public class AdminController {

    private final AdminService adminService;

    //3-Operation
    @Operation(summary = "Crear un nuevo administrador", description = "Registra un nuevo administrador en el sistema aplicando validaciones de correo único y campos obligatorios.")
    //4-API Responses
    @ApiResponses(value = {
        // 4.1  Response
        @ApiResponse(responseCode = "201", description = "Administrador creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o formato incorrecto")
    })
    /**
     * Crea un nuevo administrador en el sistema.
     *
     * @param admin los datos del administrador a crear.
     * @return una respuesta con el administrador creado y el estado HTTP 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<Admin> createAdmin(@Valid @RequestBody Admin admin) {
        Admin newAdmin = adminService.createAdmin(admin);
        return new ResponseEntity<>(newAdmin, HttpStatus.CREATED);
    }

    //5-Operation
    @Operation(summary = "Obtener administradores activos", description = "Recupera una lista con todos los administradores que no han sido dados de baja lógicamente.")
    //6-API Responses
    @ApiResponses(value = {
        // 6.1  Response
        @ApiResponse(responseCode = "200", description = "Lista de administradores activos recuperada")
    })
    /**
     * Obtiene una lista de todos los administradores activos en el sistema.
     *
     * @return una respuesta con la lista de administradores activos y el estado HTTP 200 (OK).
     */
    @GetMapping("/active")
    public ResponseEntity<List<Admin>> getActiveAdmins() {
        List<Admin> admins = adminService.getActiveAdmins();
        return ResponseEntity.ok(admins);
    }

    @Operation(summary = "Buscar administrador por email", description = "Busca los detalles de un administrador utilizando su dirección de correo electrónico.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Administrador encontrado"),
        @ApiResponse(responseCode = "404", description = "No se encontró ningún administrador con el email provisto")
    })
    /**
     * Obtiene un administrador específico a partir de su correo electrónico.
     *
     * @param email el correo electrónico del administrador a buscar.
     * @return una respuesta con el administrador encontrado o estado HTTP 404 (NOT FOUND) si no existe.
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<Admin> getAdminByEmail(@PathVariable String email) {
        return adminService.getAdminByEmail(email)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Desactivar un administrador (Baja Lógica)", description = "Cambia el estado del administrador a inactivo mapeando su ID en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Administrador deactivated correctamente"),
        @ApiResponse(responseCode = "404", description = "Administrador no encontrado")
    })
    /**
     * Desactiva un administrador (baja lógica) mediante su ID.
     *
     * @param id el identificador del administrador a desactivar.
     * @return una respuesta con el administrador actualizado o estado HTTP 404 (NOT FOUND) si no existe.
     */
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Admin> deactivateAdmin(@PathVariable Long id) {
        try {
            Admin deactivatedAdmin = adminService.deactivateAdmin(id);
            return ResponseEntity.ok(deactivatedAdmin);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Actualizar datos de un administrador", description = "Reemplaza por completo los datos de un administrador existente identificado por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Administrador actualizado con éxito"),
        @ApiResponse(responseCode = "400", description = "Validación fallida en los datos del body"),
        @ApiResponse(responseCode = "404", description = "Administrador no encontrado")
    })
    /**
     * Actualiza los datos de un administrador existente.
     *
     * @param id el identificador del administrador a actualizar.
     * @param adminDetails los nuevos datos del administrador.
     * @return una respuesta con el administrador actualizado y el estado HTTP 200 (OK).
     */
    @PutMapping("/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable Long id, @Valid @RequestBody Admin adminDetails) {
        Admin updatedAdmin = adminService.updateAdmin(id, adminDetails);
        return ResponseEntity.ok(updatedAdmin);
    }

    @Operation(summary = "Eliminar físicamente un administrador", description = "Remueve permanentemente un registro de administrador de la base de datos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Administrador eliminado correctamente, sin contenido de retorno"),
        @ApiResponse(responseCode = "404", description = "Administrador no encontrado para eliminación")
    })
    /**
     * Elimina físicamente un administrador de la base de datos.
     *
     * @param id el identificador del administrador a eliminar.
     * @return una respuesta con estado HTTP 204 (NO CONTENT).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }
}