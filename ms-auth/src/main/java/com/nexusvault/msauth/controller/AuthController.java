package com.nexusvault.msauth.controller;

import com.nexusvault.msauth.model.ModelAuth;
import com.nexusvault.msauth.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthRepository authRepository;

    @GetMapping("/users")
    public List<ModelAuth> getAllAuthUsers() {
        return authRepository.findAll();
    }
}