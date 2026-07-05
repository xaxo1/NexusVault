package com.nexusvault.msreports.dto;

import java.util.List;

/**
 * DTO que consolida el resumen de la actividad de un usuario, usado habitualmente en la estructura de los reportes.
 *
 * @param perfil El perfil del usuario, traído del microservicio de usuarios.
 * @param historialOrdenes El listado con todas las órdenes del usuario.
 * @param totalInvertido La sumatoria del total invertido por este usuario.
 */
public record ResumenUsuarioReportDTO(
    UserRemoteDTO perfil,
    List<OrderRemoteDTO> historialOrdenes,
    Double totalInvertido
) {}