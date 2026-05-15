package com.sebastian.pastry3ddemo.dto.common;

import java.time.OffsetDateTime;

public record ErrorResponse(
        String error,
        String message,
        OffsetDateTime timestamp
) {}
