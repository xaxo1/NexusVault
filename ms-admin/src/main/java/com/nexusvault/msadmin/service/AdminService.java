package com.nexusvault.msadmin.service;

import com.nexusvault.msadmin.model.Admin;
import com.nexusvault.msadmin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // Lombok genera el constructor para inyectar el repositorio automáticamente
public class AdminService {

    private final AdminRepository adminRepository;

    @Transactional
    public Admin createAdmin(Admin admin) {
        // Aquí podrías agregar validaciones extra, ej: verificar si el email ya existe
        if (adminRepository.findByEmail(admin.getEmail()).isPresent()) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        return adminRepository.save(admin);
    }

    @Transactional(readOnly = true)
    public List<Admin> getActiveAdmins() {
        return adminRepository.findByActiveTrue();
    }

    @Transactional(readOnly = true)
    public Optional<Admin> getAdminByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    @Transactional
    public Admin deactivateAdmin(Long id) {
        // Buscamos el admin o lanzamos un error si no existe
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Administrador no encontrado con ID: " + id));
        
        // Baja lógica
        admin.setActive(false);
        return adminRepository.save(admin);
    }
}