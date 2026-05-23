package com.nexusvault.msauth.service;

import com.nexusvault.msauth.dto.AuthRequestDTO;
import com.nexusvault.msauth.dto.AuthResponseDTO;
import com.nexusvault.msauth.model.AuthModel;
import com.nexusvault.msauth.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthRepository authRepository;

    public AuthModel saveUser(AuthModel user) {
        log.info("Registrando nuevo usuario en el sistema de autenticación: {}", user.getEmail());
        return authRepository.save(user);
    }

    public Optional<AuthModel> findUserById(Long id) {
        return authRepository.findById(id);
    }

    public Optional<AuthResponseDTO> authenticateUser(AuthRequestDTO request) {
        Optional<AuthModel> userOpt = authRepository.findByEmail(request.getEmail());

        if (userOpt.isPresent()) {
            AuthModel user = userOpt.get();

            // 1. Validar estado de la cuenta
            if (!user.getIsActive()) {
                log.warn("Intento de login rechazado: La cuenta {} está inactiva.", request.getEmail());
                return Optional.empty();
            }

            // 2. Validación de contraseña en texto plano (Simulación base)
            if (user.getPassword().equals(request.getPassword())) {
                log.info("Autenticación exitosa para el usuario: {}", request.getEmail());
                
                // SIMULACIÓN DE GENERACIÓN DE JWT: Armamos un token representativo estructurado
                String generatedJwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." + 
                                       java.util.Base64.getEncoder().encodeToString(user.getEmail().getBytes()) + 
                                       ".NEXUS_SECRET_KEY";

                return Optional.of(new AuthResponseDTO(generatedJwt, user.getEmail(), user.getRole()));
            }
        }

        return Optional.empty();
    }
}