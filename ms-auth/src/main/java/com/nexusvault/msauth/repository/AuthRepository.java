package com.nexusvault.msauth.repository;

import com.nexusvault.msauth.model.ModelAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<ModelAuth, Long> {

    // Método vital para el sistema de login: buscar al usuario por su correo
    Optional<ModelAuth> findByEmail(String email);
}