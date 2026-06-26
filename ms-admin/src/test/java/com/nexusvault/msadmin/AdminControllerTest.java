package com.nexusvault.msadmin;

import com.nexusvault.msadmin.controller.AdminController;
import com.nexusvault.msadmin.model.Admin;
import com.nexusvault.msadmin.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    void debeCrearAdmin() throws Exception {
        Admin admin = new Admin();
        admin.setName("Ignacio UC");
        admin.setEmail("test@nexusvault.cl");
        admin.setPassword("ClaveSegura123");

        when(adminService.createAdmin(any(Admin.class))).thenReturn(admin);

        // Forzamos el JSON manual para asegurarnos de que el validador lea el password
        String adminJson = "{\"name\":\"Ignacio UC\",\"email\":\"test@nexusvault.cl\",\"password\":\"ClaveSegura123\"}";

        mockMvc.perform(post("/api/v1/admins")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(adminJson))
                .andExpect(status().isCreated());
    }

    @Test
    void debeObtenerActivos() throws Exception {
        Admin admin = new Admin();
        when(adminService.getActiveAdmins()).thenReturn(List.of(admin));

        mockMvc.perform(get("/api/v1/admins/active"))
                .andExpect(status().isOk());
    }

    @Test
    void debeBuscarPorEmail_CuandoExiste() throws Exception {
        Admin admin = new Admin();
        when(adminService.getAdminByEmail("test@nexusvault.cl")).thenReturn(Optional.of(admin));

        mockMvc.perform(get("/api/v1/admins/email/test@nexusvault.cl"))
                .andExpect(status().isOk());
    }

    @Test
    void debeBuscarPorEmail_CuandoNoExiste() throws Exception {
        when(adminService.getAdminByEmail("no@existe.cl")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/admins/email/no@existe.cl"))
                .andExpect(status().isNotFound());
    }

    @Test
    void debeDesactivarAdmin_CuandoExiste() throws Exception {
        Admin admin = new Admin();
        when(adminService.deactivateAdmin(1L)).thenReturn(admin);

        mockMvc.perform(patch("/api/v1/admins/1/deactivate"))
                .andExpect(status().isOk());
    }

    @Test
    void debeDesactivarAdmin_CuandoLanzaExcepcion() throws Exception {
        when(adminService.deactivateAdmin(99L)).thenThrow(new RuntimeException("No encontrado"));

        mockMvc.perform(patch("/api/v1/admins/99/deactivate"))
                .andExpect(status().isNotFound());
    }

    @Test
    void debeActualizarAdmin() throws Exception {
        Admin admin = new Admin();
        admin.setName("Ignacio Actualizado");
        admin.setEmail("update@nexusvault.cl");
        admin.setPassword("NuevaClave123");

        when(adminService.updateAdmin(eq(1L), any(Admin.class))).thenReturn(admin);

        // Forzamos el JSON manual para el método PUT
        String adminJsonUpdate = "{\"name\":\"Ignacio Actualizado\",\"email\":\"update@nexusvault.cl\",\"password\":\"NuevaClave123\"}";

        mockMvc.perform(put("/api/v1/admins/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(adminJsonUpdate))
                .andExpect(status().isOk());
    }

    @Test
    void debeEliminarAdmin() throws Exception {
        mockMvc.perform(delete("/api/v1/admins/1"))
                .andExpect(status().isNoContent());
    }
}