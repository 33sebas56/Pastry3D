package com.sebastian.pastry3ddemo.dto.dessert;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

public record DessertProjectResponse(
        UUID id,
        String prompt,
        Map<String, Object> recipeJson,
        String thumbnailUrl,
        OffsetDateTime createdAt
) {}