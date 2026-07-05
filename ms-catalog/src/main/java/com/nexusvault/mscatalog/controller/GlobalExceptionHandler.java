package com.nexusvault.mscatalog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase que centraliza el manejo de excepciones de todo el microservicio.
 * Evita tener que usar bloques try/catch en cada método de los controladores.
 * Se encarga de capturar errores y retornar respuestas HTTP formateadas.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja las excepciones generadas por fallas en la validación de argumentos (@Valid).
     *
     * @param ex la excepción lanzada cuando los datos no cumplen con las restricciones.
     * @return un mapa con los errores por campo y el estado HTTP 400 (BAD REQUEST).
     */
    // Maneja los errores de validación (@Valid) como cuando falta un campo obligatorio
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errores.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

    /**
     * Maneja excepciones generales no controladas para evitar caídas en la aplicación.
     *
     * @param ex la excepción general capturada.
     * @return un mapa con un mensaje de error y el estado HTTP 500 (INTERNAL SERVER ERROR).
     */
    // Maneja cualquier otro error inesperado para que no se caiga la app
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Ocurrió un error en el servidor: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
