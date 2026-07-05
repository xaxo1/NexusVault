package com.nexusvault.msadmin.repository;

import com.nexusvault.msadmin.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad {@link Admin}.
 * Proporciona métodos para realizar operaciones de acceso a datos sobre administradores.
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    /**
     * Busca un administrador por su dirección de correo electrónico.
     *
     * @param email el correo electrónico del administrador a buscar.
     * @return un {@link Optional} que contiene el administrador si se encuentra, o vacío si no.
     */
    // VITAL: Para el login o para evitar correos duplicados
    Optional<Admin> findByEmail(String email);

    /**
     * Obtiene una lista de todos los administradores activos.
     *
     * @return una lista de administradores que no han sido dados de baja.
     */
    // Para obtener solo a los admins que no han sido baneados o desactivados
    List<Admin> findByActiveTrue();
}