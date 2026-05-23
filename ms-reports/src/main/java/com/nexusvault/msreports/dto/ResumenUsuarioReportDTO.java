package com.nexusvault.msreports.dto;

import java.util.List;

public record ResumenUsuarioReportDTO(
    UserRemoteDTO perfil,
    List<OrderRemoteDTO> historialOrdenes,
    Double totalInvertido
) {}