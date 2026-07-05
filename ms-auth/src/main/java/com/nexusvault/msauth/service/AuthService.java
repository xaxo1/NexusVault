package com.nexusvault.msauth.service;

import com.nexusvault.msauth.dto.AuthRequestDTO;
import com.nexusvault.msauth.dto.AuthResponseDTO;
import com.nexusvault.msauth.model.AuthModel;
import com.nexusvault.msauth.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servicio encargado de la lógica de negocio de la autenticación.
 * Gestiona el registro de usuarios, validación de credenciales y generación de tokens.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder; // Inyectado para hashing seguro

    /**
     * Registra de manera segura a un nuevo usuario en el sistema.
     * Se encarga de encriptar la contraseña antes de persistirla.
     *
     * @param user la entidad del usuario a registrar.
     * @return el modelo de usuario persistido.
     */
    public AuthModel saveUser(AuthModel user) {
        log.info("Registrando nuevo usuario en el sistema de autenticación: {}", user.getEmail());
        // Encriptar la contraseña antes de guardarla en la base de datos
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return authRepository.save(user);
    }

    /**
     * Busca un usuario por su identificador.
     *
     * @param id el identificador único del usuario.
     * @return un {@link Optional} con el usuario encontrado o vacío.
     */
    public Optional<AuthModel> findUserById(Long id) {
        return authRepository.findById(id);
    }

    /**
     * Autentica un usuario verificando sus credenciales y estado activo.
     *
     * @param request el objeto con el email y contraseña del usuario.
     * @return un {@link Optional} con el DTO de respuesta (incluye token JWT) si el login es exitoso.
     */
    public Optional<AuthResponseDTO> authenticateUser(AuthRequestDTO request) {
        Optional<AuthModel> userOpt = authRepository.findByEmail(request.getEmail());

        if (userOpt.isPresent()) {
            AuthModel user = userOpt.get();

            // 1. Validar estado de la cuenta
            if (!user.getIsActive()) {
                log.warn("Intento de login rechazado: La cuenta {} está inactiva.", request.getEmail());
                return Optional.empty();
            }

            // 2. Validación utilizando descifrado seguro BCrypt
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                log.info("Autenticación exitosa para el usuario: {}", request.getEmail());
                
                java.security.Key key = io.jsonwebtoken.security.Keys.hmacShaKeyFor("NEXUS_SECRET_KEY_SUPER_SECRETA_Y_LARGA_1234567890".getBytes());
                
                String generatedJwt = io.jsonwebtoken.Jwts.builder()
                        .setSubject(user.getEmail())
                        .claim("role", user.getRole())
                        .setIssuedAt(new java.util.Date())
                        .setExpiration(new java.util.Date(System.currentTimeMillis() + 86400000)) // 24 horas
                        .signWith(key, io.jsonwebtoken.SignatureAlgorithm.HS256)
                        .compact();

                return Optional.of(new AuthResponseDTO(generatedJwt, user.getEmail(), user.getRole()));
            } else {
                log.warn("Contraseña incorrecta para el usuario: {}", request.getEmail());
            }
        }

        return Optional.empty();
    }
}