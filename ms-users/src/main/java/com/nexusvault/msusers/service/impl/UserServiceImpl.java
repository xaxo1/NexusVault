package com.nexusvault.msusers.service.impl;

import com.nexusvault.msusers.model.UserModel;
import com.nexusvault.msusers.repository.UserRepository;
import com.nexusvault.msusers.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UserModel> obtenerTodosLosPerfiles() {
        log.info("Obteniendo todos los perfiles de usuario");
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserModel> obtenerPorAuthId(Long authId) {
        log.info("Buscando perfil por Auth ID: {}", authId);
        return userRepository.findByAuthId(authId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserModel> obtenerPorNickname(String nickname) {
        log.info("Buscando perfil por Nickname: {}", nickname);
        return userRepository.findByNickname(nickname);
    }

    @Override
    @Transactional
    public UserModel crearPerfil(UserModel userModel) {
        log.info("Creando nuevo perfil de usuario con nickname: {}", userModel.getNickname());
        return userRepository.save(userModel);
    }

    @Override
    @Transactional
    public Optional<UserModel> actualizarPerfil(Long id, UserModel userModel) {
        log.info("Intentando actualizar perfil con ID: {}", id);
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setNickname(userModel.getNickname());
            existingUser.setAvatarUrl(userModel.getAvatarUrl());
            log.info("Perfil actualizado correctamente: {}", id);
            return userRepository.save(existingUser);
        });
    }

    @Override
    @Transactional
    public void eliminarPerfil(Long id) {
        log.info("Eliminando perfil con ID: {}", id);
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("No se puede eliminar: No existe ningún perfil con el ID: " + id);
        }
        userRepository.deleteById(id);
    }
}