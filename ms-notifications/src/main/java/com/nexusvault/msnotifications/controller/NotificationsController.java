package com.nexusvault.msnotifications.controller;

import com.nexusvault.msnotifications.dto.NotificationRequestDTO;
import com.nexusvault.msnotifications.model.ModelNotifications;
import com.nexusvault.msnotifications.model.NotificationStatus;
import com.nexusvault.msnotifications.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para el manejo y auditoría de notificaciones en el sistema.
 * Proporciona endpoints para solicitar envíos, consultar el estado de las notificaciones
 * y confirmar su despacho.
 */
@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
//1-acá el tag general
@Tag(name = "Notificaciones", description = "Endpoints para el encolamiento, confirmación de envíos y auditoría de alertas del sistema")
public class NotificationsController {

    private final NotificationService notificationService;

    /**
     * Solicita el registro y encolamiento de una nueva notificación.
     *
     * @param requestDTO Objeto con la información requerida para el envío (destinatario, título, mensaje).
     * @return ResponseEntity con la entidad de notificación creada en estado PENDING.
     */
    @PostMapping("/send")
    @Operation(summary = "Solicitar el envío de una notificación", description = "Recibe los datos de un mensaje para un usuario externo y lo registra con estado inicial PENDING para su posterior despacho.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Notificación encolada de forma exitosa"),
        @ApiResponse(responseCode = "400", description = "Estructura DTO inválida o campos obligatorios vacíos", content = @Content)
    })
    public ResponseEntity<ModelNotifications> requestNotification(@Valid @RequestBody NotificationRequestDTO requestDTO) {
        ModelNotifications created = notificationService.createNotification(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Obtiene el historial completo de todas las notificaciones registradas.
     *
     * @return ResponseEntity con una lista que contiene todos los registros de notificaciones.
     */
    @GetMapping("/logs")
    @Operation(summary = "Obtener el historial de todas las notificaciones", description = "Recupera la lista histórica de todos los registros de alertas emitidos por la plataforma.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Historial cargado correctamente")
    })
    public ResponseEntity<List<ModelNotifications>> getAllLogs() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    /**
     * Retorna una lista de notificaciones que actualmente se encuentran pendientes de envío (estado PENDING).
     *
     * @return ResponseEntity con las notificaciones no despachadas aún.
     */
    @GetMapping("/pending")
    @Operation(summary = "Listar notificaciones pendientes de envío", description = "Filtra de forma exclusiva aquellas alertas que todavía no han sido procesadas o despachadas por los servidores de correo.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado de pendientes recuperado con éxito")
    })
    public ResponseEntity<List<ModelNotifications>> getPending() {
        return ResponseEntity.ok(notificationService.getNotificationsByStatus(NotificationStatus.PENDING));
    }

    /**
     * Confirma el despacho exitoso de una notificación marcándola como enviada (SENT).
     *
     * @param id Identificador de la notificación a confirmar.
     * @return ResponseEntity con el registro de notificación modificado.
     */
    @PatchMapping("/{id}/confirm")
    @Operation(summary = "Confirmar despacho de la notificación", description = "Modifica el estado de una notificación a 'SENT' y estampa la marca de tiempo exacta de salida.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estado actualizado a SENT con éxito"),
        @ApiResponse(responseCode = "404", description = "No se encontró ninguna notificación con el ID suministrado", content = @Content)
    })
    public ResponseEntity<ModelNotifications> confirmDelivery(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.markAsSent(id));
    }
}