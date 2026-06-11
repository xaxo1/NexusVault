package com.nexusvault.msadmin.controller;

import com.nexusvault.msadmin.model.Admin;
import com.nexusvault.msadmin.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // POST: /api/v1/admins
    @PostMapping
    public ResponseEntity<Admin> createAdmin(@Valid @RequestBody Admin admin) {
        // Usamos @Valid para que Spring ejecute las reglas @NotBlank y @Email de tu modelo
        Admin newAdmin = adminService.createAdmin(admin);
        return new ResponseEntity<>(newAdmin, HttpStatus.CREATED); // Devuelve 201 Created
    }

    // GET: /api/v1/admins/active
    @GetMapping("/active")
    public ResponseEntity<List<Admin>> getActiveAdmins() {
        List<Admin> admins = adminService.getActiveAdmins();
        return ResponseEntity.ok(admins); // Devuelve 200 OK
    }

    // GET: /api/v1/admins/email/{email}
    @GetMapping("/email/{email}")
    public ResponseEntity<Admin> getAdminByEmail(@PathVariable String email) {
        return adminService.getAdminByEmail(email)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // Devuelve 404 si no existe
    }

    // PATCH: /api/v1/admins/{id}/deactivate
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Admin> deactivateAdmin(@PathVariable Long id) {
        // Usamos PATCH porque solo estamos modificando un estado parcial (active = false)
        try {
            Admin deactivatedAdmin = adminService.deactivateAdmin(id);
            return ResponseEntity.ok(deactivatedAdmin);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // PUT: /api/v1/admins/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable Long id, @Valid @RequestBody Admin adminDetails) {
        Admin updatedAdmin = adminService.updateAdmin(id, adminDetails);
        return ResponseEntity.ok(updatedAdmin);
    }

    // DELETE: /api/v1/admins/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build(); // Devuelve 204 No Content
    }

}