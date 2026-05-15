package com.sebastian.pastry3ddemo.dto.auth;

public record AuthResponse(
        String token,
        String tokenType,
        String userId,
        String name,
        String email
) {}
