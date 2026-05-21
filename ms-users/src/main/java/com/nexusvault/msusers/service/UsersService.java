package com.nexusvault.msusers.service;

import com.nexusvault.msusers.model.UserModel;
import com.nexusvault.msusers.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * BUSCAR USUARIO POR ID
     */
    public Optional<UserModel> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * LÓGICA DE NEGOCIO: CREAR USUARIO
     * Verificamos que el nickname no esté ocupado antes de guardar.
     */
    public UserModel createUser(UserModel newUser) {
        // Asumiendo que tienes un método findByNickname en tu UserRepository
        Optional<UserModel> existingUser = userRepository.findByNickname(newUser.getNickname());

        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("El nickname '" + newUser.getNickname() + "' ya está en uso.");
        }

        return userRepository.save(newUser);
    }

    /**
     * LÓGICA DE NEGOCIO: ACTUALIZAR PERFIL (Ej: cambiar avatar o nickname)
     */
    public UserModel updateUserProfile(Long id, UserModel updatedData) {
        Optional<UserModel> userOpt = userRepository.findById(id);

        if (userOpt.isPresent()) {
            UserModel existingUser = userOpt.get();

            // Actualizamos los campos permitidos
            if (updatedData.getNickname() != null) {
                // Aquí podrías agregar otra validación para que no cambie a un nickname que ya existe
                existingUser.setNickname(updatedData.getNickname());
            }
            if (updatedData.getAvatarUrl() != null) {
                existingUser.setAvatarUrl(updatedData.getAvatarUrl());
            }

            return userRepository.save(existingUser);
        }

        return null; // Retornamos null si el usuario no existe
    }
}