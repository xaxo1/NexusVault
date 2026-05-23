package com.nexusvault.mswallet.dto;

public record NotificationRequestDTO(
    Long userId,
    String targetEmail,
    String title,
    String message
) {}