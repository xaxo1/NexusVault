package com.nexusvault.msadmin;

import com.nexusvault.msadmin.model.AuditDetail;
import com.nexusvault.msadmin.model.AuditLog;
import com.nexusvault.msadmin.repository.AuditLogRepository;
import com.nexusvault.msadmin.service.AuditService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuditServiceTest {

    @Mock
    private AuditLogRepository auditLogRepository;

    @InjectMocks
    private AuditService auditService;

    @Test
    void cuandoCrearRegistroAuditoria_sinDetalles_debeGuardarCorrectamente() {
        // GIVEN
        AuditLog logEsperado = new AuditLog();
        logEsperado.setAdminId(1L);
        logEsperado.setAction("CREATE");
        logEsperado.setTargetEntity("Admin");

        when(auditLogRepository.save(any(AuditLog.class))).thenReturn(logEsperado);

        // WHEN
        AuditLog resultado = auditService.createAuditRecord(1L, "CREATE", "Admin", null);

        // THEN
        assertNotNull(resultado);
        assertEquals("CREATE", resultado.getAction());
        verify(auditLogRepository, times(1)).save(any(AuditLog.class));
    }

    @Test
    void cuandoCrearRegistroAuditoria_conDetalles_debeVincularYGuardar() {
        // GIVEN
        List<AuditDetail> detalles = new ArrayList<>();
        AuditDetail detalle = new AuditDetail();
        detalle.setFieldName("name");
        detalle.setOldValue("Viejo");
        detalle.setNewValue("Nuevo");
        detalles.add(detalle);

        AuditLog logEsperado = new AuditLog();
        logEsperado.setDetails(detalles);

        when(auditLogRepository.save(any(AuditLog.class))).thenReturn(logEsperado);

        // WHEN
        AuditLog resultado = auditService.createAuditRecord(1L, "UPDATE", "Admin", detalles);

        // THEN
        assertNotNull(resultado);
        assertFalse(resultado.getDetails().isEmpty());
        verify(auditLogRepository, times(1)).save(any(AuditLog.class));
    }

    @Test
    void cuandoBuscarLogsPorAdmin_debeRetornarLista() {
        // GIVEN
        AuditLog log = new AuditLog();
        log.setAdminId(1L);
        when(auditLogRepository.findByAdminId(1L)).thenReturn(List.of(log));

        // WHEN
        List<AuditLog> resultado = auditService.getLogsByAdmin(1L);

        // THEN
        assertEquals(1, resultado.size());
        verify(auditLogRepository, times(1)).findByAdminId(1L);
    }

    @Test
    void cuandoBuscarLogsPorEntidad_debeRetornarLista() {
        // GIVEN
        AuditLog log = new AuditLog();
        log.setTargetEntity("Product");
        when(auditLogRepository.findByTargetEntity("Product")).thenReturn(List.of(log));

        // WHEN
        List<AuditLog> resultado = auditService.getLogsByEntity("Product");

        // THEN
        assertEquals(1, resultado.size());
        verify(auditLogRepository, times(1)).findByTargetEntity("Product");
    }
}