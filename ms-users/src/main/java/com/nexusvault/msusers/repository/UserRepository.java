package com.nexusvault.msusers.repository;

import com.nexusvault.msusers.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    // Método clave para buscar el perfil basado en la credencial del ms-auth
    Optional<UserModel> findByAuthId(Long authId);

    // Método para buscar por nickname
    Optional<UserModel> findByNickname(String nickname);
}