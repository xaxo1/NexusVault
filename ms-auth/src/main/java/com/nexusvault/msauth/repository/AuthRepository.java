package com.nexusvault.msauth.repository;

import com.nexusvault.msauth.model.AuthModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad {@link AuthModel}.
 * Permite acceder a los datos de autenticación persistidos en la base de datos.
 */
@Repository
public interface AuthRepository extends JpaRepository<AuthModel, Long> {

    /**
     * Busca un modelo de autenticación utilizando el correo electrónico del usuario.
     *
     * @param email el correo electrónico a buscar.
     * @return un {@link Optional} que contiene el usuario si existe.
     */
    // Método vital para el sistema de login: buscar al usuario por su correo
    Optional<AuthModel> findByEmail(String email);
}