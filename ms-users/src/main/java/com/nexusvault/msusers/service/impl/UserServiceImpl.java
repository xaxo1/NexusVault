package com.nexusvault.msusers.service.impl;

import com.nexusvault.msusers.model.UserModel;
import com.nexusvault.msusers.repository.UserRepository;
import com.nexusvault.msusers.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UserModel> obtenerTodosLosPerfiles() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserModel> obtenerPorAuthId(Long authId) {
        return userRepository.findByAuthId(authId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserModel> obtenerPorNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }
}