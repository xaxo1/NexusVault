package com.nexusvault.msauth.repository;

import com.nexusvault.msauth.model.AuthModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<AuthModel, Long> {

    // Método vital para el sistema de login: buscar al usuario por su correo
    Optional<AuthModel> findByEmail(String email);
}