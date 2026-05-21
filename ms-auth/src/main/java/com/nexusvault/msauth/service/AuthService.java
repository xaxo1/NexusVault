package com.nexusvault.msauth.service;

import com.nexusvault.msauth.model.AuthModel;
import com.nexusvault.msauth.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepository;

    /**
     * MÉTODOS BÁSICOS (CRUD)
     */

    public AuthModel saveUser(AuthModel user) {
        return authRepository.save(user);
    }

    public Optional<AuthModel> findUserById(Long id) {
        return authRepository.findById(id);
    }

    /**
     * LÓGICA DE NEGOCIO: LOGIN BÁSICO
     * Este método es la semilla de lo que después se convertirá en tu JWT.
     */
    public boolean authenticateUser(String email, String rawPassword) {
        Optional<AuthModel> userOpt = authRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            AuthModel user = userOpt.get();

            // 1. Verificamos que el usuario no esté baneado o desactivado
            if (!user.getIsActive()) {
                return false;
            }

            // 2. Comparamos contraseñas (OJO: Por ahora es texto plano.
            // En producción aquí se usaría BCrypt.matches() de Spring Security)
            if (user.getPassword().equals(rawPassword)) {
                return true; // ¡Login exitoso!
            }
        }

        return false; // Email no existe o contraseña incorrecta
    }
}