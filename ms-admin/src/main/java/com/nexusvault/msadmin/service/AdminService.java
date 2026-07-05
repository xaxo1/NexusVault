package com.nexusvault.msadmin.service;

import com.nexusvault.msadmin.exception.ResourceNotFoundException;
import com.nexusvault.msadmin.model.Admin;
import com.nexusvault.msadmin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio encargado de la lógica de negocio para la gestión de administradores.
 * Proporciona métodos para crear, actualizar, eliminar y consultar administradores.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    /**
     * Crea y registra un nuevo administrador en la base de datos.
     *
     * @param admin el objeto administrador a crear.
     * @return el administrador creado y persistido.
     * @throws IllegalArgumentException si el correo electrónico ya se encuentra registrado.
     */
    @Transactional
    public Admin createAdmin(Admin admin) {
        log.info("Iniciando proceso de creación para el admin con email: {}", admin.getEmail());

        if (adminRepository.findByEmail(admin.getEmail()).isPresent()) {
            log.warn("Intento de creación fallido: El email {} ya está registrado", admin.getEmail());
            throw new IllegalArgumentException("El email ya está registrado");
        }

        // NOTA: Cuando integres Spring Security, aquí harías: 
        // admin.setPassword(passwordEncoder.encode(admin.getPassword()));

        Admin savedAdmin = adminRepository.save(admin);
        log.info("Administrador creado exitosamente con ID: {}", savedAdmin.getId());
        return savedAdmin;
    }

    /**
     * Obtiene todos los administradores que se encuentran activos.
     *
     * @return una lista de administradores activos.
     */
    @Transactional(readOnly = true)
    public List<Admin> getActiveAdmins() {
        log.info("Consultando la lista de todos los administradores activos");
        return adminRepository.findByActiveTrue();
    }

    /**
     * Busca un administrador por su correo electrónico.
     *
     * @param email el correo electrónico a buscar.
     * @return un {@link Optional} con el administrador encontrado o vacío.
     */
    @Transactional(readOnly = true)
    public Optional<Admin> getAdminByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    /**
     * Realiza la baja lógica (desactivación) de un administrador mediante su ID.
     *
     * @param id el identificador del administrador a desactivar.
     * @return el administrador desactivado.
     * @throws ResourceNotFoundException si no se encuentra el administrador con el ID proporcionado.
     */
    @Transactional
    public Admin deactivateAdmin(Long id) {
        log.info("Iniciando solicitud de baja lógica para el administrador ID: {}", id);
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Fallo al desactivar: No se encontró ningún administrador con ID: {}", id);
                    return new ResourceNotFoundException("Administrador no encontrado con ID: " + id);
                });

        admin.setActive(false);
        log.info("Administrador ID: {} desactivado exitosamente (baja lógica)", id);
        return adminRepository.save(admin);
    }

    /**
     * Actualiza la información de un administrador existente.
     *
     * @param id el identificador del administrador a actualizar.
     * @param adminDetails los detalles actualizados del administrador.
     * @return el administrador actualizado.
     * @throws ResourceNotFoundException si no se encuentra el administrador con el ID proporcionado.
     */
    @Transactional
    public Admin updateAdmin(Long id, Admin adminDetails) {
        log.info("Actualizando datos del administrador ID: {}", id);
        Admin existingAdmin = adminRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Fallo al actualizar: Administrador no encontrado con ID: {}", id);
                    return new ResourceNotFoundException("Administrador no encontrado con ID: " + id);
                });

        existingAdmin.setName(adminDetails.getName());
        existingAdmin.setEmail(adminDetails.getEmail());
        existingAdmin.setRole(adminDetails.getRole());
        existingAdmin.setActive(adminDetails.isActive());
        
        if (adminDetails.getPassword() != null && !adminDetails.getPassword().isBlank()) {
            existingAdmin.setPassword(adminDetails.getPassword()); // Encriptar aquí a futuro
        }

        return adminRepository.save(existingAdmin);
    }

    /**
     * Elimina de forma permanente un administrador de la base de datos.
     *
     * @param id el identificador del administrador a eliminar.
     * @throws ResourceNotFoundException si no se encuentra el administrador con el ID proporcionado.
     */
    @Transactional
    public void deleteAdmin(Long id) {
        log.info("Ejecutando borrado físico para el administrador ID: {}", id);
        if (!adminRepository.existsById(id)) {
            log.error("Fallo al borrar: Administrador no encontrado con ID: {}", id);
            throw new ResourceNotFoundException("Administrador no encontrado con ID: " + id);
        }
        adminRepository.deleteById(id);
        log.info("Administrador ID: {} eliminado físicamente de la base de datos", id);
    }
}