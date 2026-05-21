package com.nexusvault.msusers.controller;

import com.nexusvault.msusers.model.UserModel;
import com.nexusvault.msusers.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/profiles")
    public List<UserModel> getAllProfiles() {
        return userRepository.findAll();
    }
}