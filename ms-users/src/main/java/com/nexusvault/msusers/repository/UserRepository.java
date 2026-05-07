package com.nexusvault.msusers.repository;

import com.nexusvault.msusers.model.ModelUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<ModelUser, Long> {

    // Método clave para buscar el perfil basado en la credencial del ms-auth
    Optional<ModelUser> findByAuthId(Long authId);

    // Método para buscar por nickname
    Optional<ModelUser> findByNickname(String nickname);
}