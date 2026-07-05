package com.nexusvault.msreports.dto;

/**
 * DTO remoto que modela los datos principales del perfil de un usuario, proveniente del microservicio de usuarios.
 *
 * @param id El identificador único del usuario.
 * @param nickname El apodo o nombre de usuario visible.
 * @param role El rol o nivel de privilegios que tiene el usuario en el sistema.
 */
public record UserRemoteDTO(
    Long id,
    String nickname,
    String role
) {}