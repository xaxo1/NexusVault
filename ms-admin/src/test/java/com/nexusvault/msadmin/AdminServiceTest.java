package com.nexusvault.msadmin;

import com.nexusvault.msadmin.model.Admin;
import com.nexusvault.msadmin.repository.AdminRepository;
import com.nexusvault.msadmin.service.AdminService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminService adminService;

    // ==========================================
    // TESTS PARA: createAdmin (POST)
    // ==========================================

    @Test
    void cuandoCrearAdmin_yEmailNoExiste_debeGuardar() {
        Admin admin = new Admin();
        admin.setEmail("nuevo@nexusvault.cl");

        when(adminRepository.findByEmail(admin.getEmail())).thenReturn(Optional.empty());
        when(adminRepository.save(any(Admin.class))).thenReturn(admin);

        Admin resultado = adminService.createAdmin(admin);

        assertNotNull(resultado);
        verify(adminRepository, times(1)).save(admin);
    }

    @Test
    void cuandoCrearAdmin_yEmailYaExiste_debeLanzarExcepcion() {
        Admin admin = new Admin();
        admin.setEmail("existe@nexusvault.cl");

        when(adminRepository.findByEmail(admin.getEmail())).thenReturn(Optional.of(admin));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            adminService.createAdmin(admin);
        });

        assertEquals("El email ya está registrado", exception.getMessage());
        verify(adminRepository, never()).save(any());
    }

    // ==========================================
    // TESTS PARA: getActiveAdmins (GET)
    // ==========================================

    @Test
    void cuandoBuscarActivos_debeRetornarLista() {
        Admin admin = new Admin();
        admin.setActive(true);

        when(adminRepository.findByActiveTrue()).thenReturn(List.of(admin));

        List<Admin> resultado = adminService.getActiveAdmins();

        assertEquals(1, resultado.size());
    }

    // ==========================================
    // TESTS PARA: getAdminByEmail (GET)
    // ==========================================

    @Test
    void cuandoBuscarPorEmail_debeRetornarAdmin() {
        Admin admin = new Admin();
        admin.setEmail("test@nexusvault.cl");

        when(adminRepository.findByEmail("test@nexusvault.cl")).thenReturn(Optional.of(admin));

        Optional<Admin> resultado = adminService.getAdminByEmail("test@nexusvault.cl");

        assertTrue(resultado.isPresent());
        assertEquals("test@nexusvault.cl", resultado.get().getEmail());
    }

    // ==========================================
    // TESTS PARA: deactivateAdmin (PATCH - Baja Lógica)
    // ==========================================

    @Test
    void cuandoDesactivarAdmin_yExiste_debeCambiarEstado() {
        Admin admin = new Admin();
        admin.setId(1L);
        admin.setActive(true);

        when(adminRepository.findById(1L)).thenReturn(Optional.of(admin));
        when(adminRepository.save(any(Admin.class))).thenReturn(admin);

        Admin resultado = adminService.deactivateAdmin(1L);

        assertFalse(resultado.isActive());
        verify(adminRepository).save(admin);
    }

    @Test
    void cuandoDesactivarAdmin_yNoExiste_debeLanzarExcepcion() {
        when(adminRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            adminService.deactivateAdmin(99L);
        });

        assertTrue(exception.getMessage().contains("Administrador no encontrado"));
    }

    // ==========================================
    // TESTS PARA: updateAdmin (PUT)
    // ==========================================

    @Test
    void cuandoActualizarAdmin_yExiste_debeModificarDatos() {
        Admin existente = new Admin();
        existente.setId(1L);
        existente.setName("Viejo Nombre");

        Admin nuevosDatos = new Admin();
        nuevosDatos.setName("Nuevo Nombre");
        nuevosDatos.setEmail("nuevo@nexusvault.cl");

        when(adminRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(adminRepository.save(any(Admin.class))).thenReturn(existente);

        Admin resultado = adminService.updateAdmin(1L, nuevosDatos);

        assertEquals("Nuevo Nombre", resultado.getName());
        verify(adminRepository).save(existente);
    }

    // ==========================================
    // TESTS PARA: deleteAdmin (DELETE - Baja Física)
    // ==========================================

    @Test
    void cuandoEliminarAdmin_yExiste_debeBorrarlo() {
        when(adminRepository.existsById(1L)).thenReturn(true);
        doNothing().when(adminRepository).deleteById(1L);

        adminService.deleteAdmin(1L);

        verify(adminRepository, times(1)).deleteById(1L);
    }

    @Test
    void cuandoEliminarAdmin_yNoExiste_debeLanzarExcepcion() {
        when(adminRepository.existsById(99L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            adminService.deleteAdmin(99L);
        });

        assertTrue(exception.getMessage().contains("Administrador no encontrado"));
    }
}