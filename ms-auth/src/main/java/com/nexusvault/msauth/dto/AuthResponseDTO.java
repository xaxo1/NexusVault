package com.nexusvault.msauth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "Respuesta devuelta tras una autenticación exitosa")
@Data
@AllArgsConstructor
public class AuthResponseDTO {
    
    @Schema(description = "Token Bearer JWT generado para autorizar peticiones subsiguientes", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;
    
    @Schema(description = "Email del usuario autenticado", example = "usuario@nexusvault.com")
    private String email;
    
    @Schema(description = "Rol operativo otorgado", example = "SUPER_ADMIN")
    private String role;
}