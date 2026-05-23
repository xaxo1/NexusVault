package com.nexusvault.msusers.service;

import com.nexusvault.msusers.model.UserModel;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserModel> obtenerTodosLosPerfiles();
    Optional<UserModel> obtenerPorAuthId(Long authId);
    Optional<UserModel> obtenerPorNickname(String nickname);
}