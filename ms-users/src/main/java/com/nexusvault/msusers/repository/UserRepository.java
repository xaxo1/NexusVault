package com.nexusvault.msusers.repository;

import com.nexusvault.msusers.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio JPA encargado de gestionar las operaciones de persistencia para la entidad {@link UserModel}.
 */
@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    /**
     * Busca y recupera el perfil asociado a un ID de autenticación específico.
     *
     * @param authId El identificador proveniente del servicio de autenticación (ms-auth).
     * @return Un objeto {@link Optional} que contiene el perfil encontrado, si existe.
     */
    // Método clave para buscar el perfil basado en la credencial del ms-auth
    Optional<UserModel> findByAuthId(Long authId);

    /**
     * Busca y recupera un perfil de usuario utilizando su seudónimo.
     *
     * @param nickname El apodo (nickname) del usuario que se desea buscar.
     * @return Un objeto {@link Optional} con el perfil coincidente.
     */
    // Método para buscar por nickname
    Optional<UserModel> findByNickname(String nickname);
}