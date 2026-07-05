package com.nexusvault.msorders.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador de asesoramiento global para capturar y procesar excepciones del microservicio de órdenes.
 * Centraliza el manejo de errores para devolver respuestas estandarizadas al cliente.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Captura excepciones provocadas por errores de validación en los modelos de entrada.
     *
     * @param ex Excepción que contiene los detalles de los campos que no pasaron la validación.
     * @return Respuesta estructurada con los errores y estado HTTP 400.
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
     * Captura excepciones de lógica de negocio o recursos que no fueron encontrados.
     *
     * @param ex Excepción lanzada cuando una operación no es válida o falta un dato clave.
     * @return Respuesta estructurada con el mensaje de error y estado HTTP 404.
     */
    // Maneja errores de lógica de negocio o recursos faltantes
    @ExceptionHandler({RuntimeException.class, IllegalArgumentException.class})
    public ResponseEntity<Map<String, String>> handleNotFoundExceptions(RuntimeException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Captura excepciones imprevistas o genéricas en la ejecución del servidor.
     *
     * @param ex Excepción general inesperada.
     * @return Respuesta estructurada con un mensaje de error interno y estado HTTP 500.
     */
    // Maneja errores inesperados del servidor
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Error interno en el ecosistema MS-Orders: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}