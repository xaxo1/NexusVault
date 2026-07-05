package com.nexusvault.mspayments.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador de asesoramiento global diseñado para atrapar excepciones en el microservicio de pagos.
 * Transforma los errores técnicos en respuestas HTTP con estructura controlada y clara.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Captura las fallas originadas por las validaciones de los DTOs (ej: montos negativos).
     *
     * @param ex Excepción de validación de argumento que contiene los detalles del fallo.
     * @return Respuesta estructurada con un listado de errores y estado HTTP 400.
     */
    // Captura fallas en DTOs (@Valid) como importes negativos o métodos nulos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errores.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

    /**
     * Captura infracciones a las reglas de negocio del dominio, tales como reembolsos inviables o cobros duplicados.
     *
     * @param ex Excepción de estado ilegal.
     * @return Respuesta estructurada con un mensaje descriptivo y estado HTTP 409.
     */
    // Captura violaciones de reglas de negocio del dominio (ej: pago ya existente o reembolso inviável)
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> handleBusinessStateExceptions(IllegalStateException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    /**
     * Captura solicitudes en las cuales un recurso o registro financiero no ha sido hallado.
     *
     * @param ex Excepción de argumento ilegal.
     * @return Respuesta estructurada con un mensaje de error y estado HTTP 404.
     */
    // Captura recursos financieros no encontrados (ej: buscar pago de una orden inexistente)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleNotFoundExceptions(IllegalArgumentException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Manejador genérico para capturar fallos imprevistos de red o de plataforma.
     *
     * @param ex Excepción general que se ha lanzado.
     * @return Respuesta estructurada con detalles del error interno y estado HTTP 500.
     */
    // Manejador genérico para fallos imprevistos de pasarelas o red
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Fallo interno crítico en la pasarela de pagos: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}