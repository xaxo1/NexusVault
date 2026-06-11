package com.nexusvault.msadmin.service;

import com.nexusvault.msadmin.model.Admin;
import com.nexusvault.msadmin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j // Anotación clave: habilita el objeto "log" automáticamente
@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    @Transactional
    public Admin createAdmin(Admin admin) {
        log.info("Iniciando proceso de creación para el admin con email: {}", admin.getEmail());

        if (adminRepository.findByEmail(admin.getEmail()).isPresent()) {
            log.warn("Intento de creación fallido: El email {} ya está registrado en el sistema", admin.getEmail());
            throw new IllegalArgumentException("El email ya está registrado");
        }

        Admin savedAdmin = adminRepository.save(admin);
        log.info("Administrador creado exitosamente en la base de datos con ID: {}", savedAdmin.getId());
        return savedAdmin;
    }

    @Transactional(readOnly = true)
    public List<Admin> getActiveAdmins() {
        log.info("Consultando la lista de todos los administradores activos");
        return adminRepository.findByActiveTrue();
    }

    @Transactional(readOnly = true)
    public Optional<Admin> getAdminByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    @Transactional
    public Admin deactivateAdmin(Long id) {
        log.info("Iniciando solicitud de baja lógica para el administrador ID: {}", id);
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Fallo al desactivar: No se encontró ningún administrador con ID: {}", id);
                    return new RuntimeException("Administrador no encontrado con ID: " + id);
                });

        admin.setActive(false);
        log.info("Administrador ID: {} desactivado exitosamente (baja lógica)", id);
        return adminRepository.save(admin);
    }

    public Admin updateAdmin(Long id, Admin adminDetails) {
        log.info("Actualizando datos del administrador ID: {}", id);
        Admin existingAdmin = adminRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Fallo al actualizar: Administrador no encontrado con ID: {}", id);
                    return new RuntimeException("Administrador no encontrado con ID: " + id);
                });

        existingAdmin.setName(adminDetails.getName());
        existingAdmin.setEmail(adminDetails.getEmail());
        existingAdmin.setRole(adminDetails.getRole());
        existingAdmin.setActive(adminDetails.isActive());

        return adminRepository.save(existingAdmin);
    }

    public void deleteAdmin(Long id) {
        log.info("Ejecutando borrado físico para el administrador ID: {}", id);
        if (!adminRepository.existsById(id)) {
            log.error("Fallo al borrar: Administrador no encontrado con ID: {}", id);
            throw new RuntimeException("Administrador no encontrado con ID: " + id);
        }
        adminRepository.deleteById(id);
        log.info("Administrador ID: {} eliminado físicamente de la base de datos", id);
    }
}