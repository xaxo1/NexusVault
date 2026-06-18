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

// @ExtendWith le dice a Spring que no levante toda la aplicación (ni la base de datos).
// Solo va a levantar Mockito, que es una librería para crear "clones falsos" de nuestras clases.
@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

	// @Mock crea un clon falso de tu repositorio.
	// No se va a conectar a MySQL. Nosotros le dictaremos qué responder en cada caso.
	@Mock
	private AdminRepository adminRepository;

	// @InjectMocks es la clase REAL que estamos poniendo a prueba.
	// Mockito va a tomar el "adminRepository" falso de arriba y se lo va a inyectar por debajo.
	@InjectMocks
	private AdminService adminService;

	// ==========================================
	// TESTS PARA: createAdmin (POST)
	// ==========================================

	@Test
	void cuandoCrearAdmin_yEmailNoExiste_debeGuardar() {
		// 1. PREPARAR (Arrange): Creamos los datos de prueba
		Admin admin = new Admin();
		admin.setEmail("nuevo@nexusvault.cl");

		// Le enseñamos al clon falso qué hacer:
		// "Cuando busquen este email, responde que está vacío (no existe)"
		when(adminRepository.findByEmail(admin.getEmail())).thenReturn(Optional.empty());
		// "Cuando intenten guardar CUALQUIER admin, responde devolviendo el mismo admin"
		when(adminRepository.save(any(Admin.class))).thenReturn(admin);

		// 2. ACTUAR (Act): Ejecutamos el método real de nuestro servicio
		Admin resultado = adminService.createAdmin(admin);

		// 3. VERIFICAR (Assert): Comprobamos que el resultado sea el esperado
		assertNotNull(resultado); // Aseguramos que no devuelva un nulo
		// Verificamos que el repositorio falso llamó al método "save" exactamente 1 vez
		verify(adminRepository, times(1)).save(admin);
	}

	@Test
	void cuandoCrearAdmin_yEmailYaExiste_debeLanzarExcepcion() {
		// 1. PREPARAR
		Admin admin = new Admin();
		admin.setEmail("existe@nexusvault.cl");

		// Simulamos que la base de datos YA TIENE registrado este correo
		when(adminRepository.findByEmail(admin.getEmail())).thenReturn(Optional.of(admin));

		// 2 y 3. ACTUAR Y VERIFICAR a la vez
		// Comprobamos que al intentar crear el admin, el sistema "explote" lanzando la excepción correcta
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			adminService.createAdmin(admin);
		});

		// Comprobamos que el mensaje de error sea exactamente el que programaste
		assertEquals("El email ya está registrado", exception.getMessage());

		// Verificamos que el método "save" NUNCA se haya ejecutado (porque la excepción bloqueó el paso)
		verify(adminRepository, never()).save(any());
	}

	// ==========================================
	// TESTS PARA: getActiveAdmins (GET)
	// ==========================================

	@Test
	void cuandoBuscarActivos_debeRetornarLista() {
		Admin admin = new Admin();
		admin.setActive(true);

		// Simulamos que la base de datos devuelve una lista con 1 administrador activo
		when(adminRepository.findByActiveTrue()).thenReturn(List.of(admin));

		List<Admin> resultado = adminService.getActiveAdmins();

		// Verificamos que la lista resultante tenga exactamente 1 elemento
		assertEquals(1, resultado.size());
	}

	// ==========================================
	// TESTS PARA: getAdminByEmail (GET)
	// ==========================================

	@Test
	void cuandoBuscarPorEmail_debeRetornarAdmin() {
		Admin admin = new Admin();
		admin.setEmail("test@nexusvault.cl");

		// Simulamos que la BD encontró al admin
		when(adminRepository.findByEmail("test@nexusvault.cl")).thenReturn(Optional.of(admin));

		Optional<Admin> resultado = adminService.getAdminByEmail("test@nexusvault.cl");

		// Comprobamos que el Optional contenga algo (esté presente) y que el email coincida
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
		admin.setActive(true); // Está activo originalmente

		// Simulamos que la BD encuentra el ID 1
		when(adminRepository.findById(1L)).thenReturn(Optional.of(admin));
		when(adminRepository.save(any(Admin.class))).thenReturn(admin);

		// Ejecutamos la baja lógica
		Admin resultado = adminService.deactivateAdmin(1L);

		// Comprobamos que su estado ahora sea FALSO (inactivo)
		assertFalse(resultado.isActive());
		verify(adminRepository).save(admin);
	}

	@Test
	void cuandoDesactivarAdmin_yNoExiste_debeLanzarExcepcion() {
		// Simulamos que la BD no encuentra el ID 99
		when(adminRepository.findById(99L)).thenReturn(Optional.empty());

		// Comprobamos que explote con un RuntimeException
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			adminService.deactivateAdmin(99L);
		});

		// Comprobamos que el mensaje contenga el texto de error
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

		// Simulamos que al buscar el ID 1, nos devuelve el admin con "Viejo Nombre"
		when(adminRepository.findById(1L)).thenReturn(Optional.of(existente));
		when(adminRepository.save(any(Admin.class))).thenReturn(existente);

		// Ejecutamos la actualización inyectando los "nuevosDatos"
		Admin resultado = adminService.updateAdmin(1L, nuevosDatos);

		// Comprobamos que el nombre resultante se haya sobrescrito correctamente
		assertEquals("Nuevo Nombre", resultado.getName());
		verify(adminRepository).save(existente);
	}

	// ==========================================
	// TESTS PARA: deleteAdmin (DELETE - Baja Física)
	// ==========================================

	@Test
	void cuandoEliminarAdmin_yExiste_debeBorrarlo() {
		// Simulamos que la BD confirma que el ID 1 sí existe
		when(adminRepository.existsById(1L)).thenReturn(true);

		// doNothing() se usa para métodos "void" (que no devuelven nada).
		// Le decimos al clon: "Cuando llamen a deleteById, no hagas nada, finge que lo borraste"
		doNothing().when(adminRepository).deleteById(1L);

		adminService.deleteAdmin(1L);

		// Verificamos que el repositorio sí recibió la orden de borrar el ID 1
		verify(adminRepository, times(1)).deleteById(1L);
	}

	@Test
	void cuandoEliminarAdmin_yNoExiste_debeLanzarExcepcion() {
		// Simulamos que el ID 99 no existe en la BD
		when(adminRepository.existsById(99L)).thenReturn(false);

		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			adminService.deleteAdmin(99L);
		});

		assertTrue(exception.getMessage().contains("Administrador no encontrado"));
	}
}