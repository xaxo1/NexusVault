package com.nexusvault.msauth.controller;

import com.nexusvault.msauth.dto.AuthRequestDTO;
import com.nexusvault.msauth.dto.AuthResponseDTO;
import com.nexusvault.msauth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
//1-acá el tag general
@Tag(name = "Autenticación", description = "Endpoints para el inicio de sesión y gestión de credenciales de acceso")
public class AuthController {

    private final AuthService authService;

    //3-Operation
    @Operation(summary = "Iniciar sesión en la plataforma", description = "Valida las credenciales del usuario y retorna un token JWT válido por 24 horas si el usuario está activo.")
    //4-API Responses
    @ApiResponses(value = {
        // 4.1  Response
        @ApiResponse(responseCode = "200", description = "Autenticación exitosa, token generado correctamente"),
        @ApiResponse(responseCode = "401", description = "Credenciales inválidas o cuenta de usuario inactiva")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequestDTO authRequest) {
        return authService.authenticateUser(authRequest)
                .map(response -> ResponseEntity.ok().body(response))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(new AuthResponseDTO(null, authRequest.getEmail(), "Credenciales inválidas o cuenta inactiva")));
    }
}