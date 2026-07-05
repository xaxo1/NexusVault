package com.nexusvault.msinventory.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador de asesoramiento global para gestionar y capturar excepciones a nivel de aplicación.
 * Transforma las excepciones internas en respuestas HTTP estandarizadas.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja errores de validación de campos provocados por las anotaciones de validación (ej. @Valid).
     *
     * @param ex Excepción lanzada cuando los argumentos de un método no son válidos.
     * @return Respuesta estructurada con los errores de validación y estado HTTP 400.
     */
    // Maneja errores de validación de campos (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errores.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

    /**
     * Maneja excepciones de lógica de negocio, como la falta de stock o parámetros incorrectos.
     *
     * @param ex Excepción de tiempo de ejecución relacionada con la lógica de negocio.
     * @return Respuesta estructurada con un mensaje de error y estado HTTP 409.
     */
    // Maneja errores de lógica de negocio (ej. Stock insuficiente) o parámetros inválidos
    @ExceptionHandler({IllegalStateException.class, IllegalArgumentException.class})
    public ResponseEntity<Map<String, String>> handleBusinessExceptions(RuntimeException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    /**
     * Maneja cualquier excepción imprevista que no tenga un manejador específico.
     *
     * @param ex Excepción general o interna del servidor.
     * @return Respuesta estructurada con un mensaje de error y estado HTTP 500.
     */
    // Maneja errores inesperados generales
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Error interno en el servidor de inventarios: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}