package com.nexusvault.msadmin.repository;

import com.nexusvault.msadmin.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    
    // Método extra para buscar a un admin por su email (muy útil para el login después)
    Optional<Admin> findByEmail(String email);
    
}