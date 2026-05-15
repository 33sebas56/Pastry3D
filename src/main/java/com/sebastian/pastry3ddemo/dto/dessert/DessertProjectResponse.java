package com.sebastian.pastry3ddemo.dto.dessert;

import com.fasterxml.jackson.databind.JsonNode;

import java.time.OffsetDateTime;
import java.util.UUID;

public record DessertProjectResponse(
        UUID id,
        String prompt,
        JsonNode recipeJson,
        String thumbnailUrl,
        OffsetDateTime createdAt
) {}
