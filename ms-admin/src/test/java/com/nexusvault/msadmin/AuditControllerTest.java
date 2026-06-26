package com.nexusvault.msadmin;

import com.nexusvault.msadmin.controller.AuditController;
import com.nexusvault.msadmin.model.AuditLog;
import com.nexusvault.msadmin.service.AuditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AuditControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuditService auditService;

    @InjectMocks
    private AuditController auditController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(auditController).build();
    }

    @Test
    void debeObtenerLogsPorAdmin() throws Exception {
        when(auditService.getLogsByAdmin(1L)).thenReturn(List.of(new AuditLog()));
        mockMvc.perform(get("/api/v1/audits/admin/1"))
                .andExpect(status().isOk());
    }

    @Test
    void debeObtenerLogsPorEntidad() throws Exception {
        when(auditService.getLogsByEntity("Admin")).thenReturn(List.of(new AuditLog()));
        mockMvc.perform(get("/api/v1/audits/entity/Admin"))
                .andExpect(status().isOk());
    }
}