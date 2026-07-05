package com.nexusvault.msnotifications.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador de asesoramiento global para capturar y manejar excepciones en el microservicio de notificaciones.
 * Convierte errores internos en respuestas HTTP claras y estructuradas.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Captura y maneja errores de validación de campos, como correos inválidos o campos vacíos.
     *
     * @param ex Excepción lanzada al fallar la validación de un argumento.
     * @return Respuesta estructurada con los errores de cada campo y estado HTTP 400.
     */
    // Captura errores cuando fallan los campos @Valid (ej. formato de correo inválido o mensajes vacíos)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errores.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

    /**
     * Captura errores de lógica de negocio o cuando un registro solicitado no existe.
     *
     * @param ex Excepción de argumento ilegal indicando que el registro no fue encontrado.
     * @return Respuesta con el mensaje de error y estado HTTP 404.
     */
    // Captura errores de negocio (ej. Buscar un ID de log que no existe)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleNotFoundException(IllegalArgumentException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Maneja cualquier excepción no prevista de forma general.
     *
     * @param ex La excepción general lanzada.
     * @return Respuesta con mensaje de error interno y estado HTTP 500.
     */
    // Errores generales imprevistos
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Error interno en el servidor de mensajería: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}