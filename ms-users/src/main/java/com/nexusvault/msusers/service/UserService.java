package com.nexusvault.msusers.service;

import com.nexusvault.msusers.model.UserModel;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserModel> obtenerTodosLosPerfiles();
    Optional<UserModel> obtenerPorAuthId(Long authId);
    Optional<UserModel> obtenerPorNickname(String nickname);
    
    // Nuevos métodos para completar el CRUD
    UserModel crearPerfil(UserModel userModel);
    Optional<UserModel> actualizarPerfil(Long id, UserModel userModel);
    void eliminarPerfil(Long id);
}