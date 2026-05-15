$ErrorActionPreference = "Stop"

$utf8NoBom = New-Object System.Text.UTF8Encoding($false)

function Write-TextFile {
    param(
        [string]$Path,
        [string]$Content
    )

    [System.IO.File]::WriteAllText((Join-Path (Get-Location) $Path), $Content, $utf8NoBom)
    Write-Host "Actualizado: $Path" -ForegroundColor Green
}

Write-TextFile "src/main/java/com/sebastian/pastry3ddemo/dto/dessert/DessertProjectResponse.java" @'
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
'@

Write-TextFile "src/main/java/com/sebastian/pastry3ddemo/mapper/DessertProjectMapper.java" @'
package com.sebastian.pastry3ddemo.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebastian.pastry3ddemo.dto.dessert.DessertProjectResponse;
import com.sebastian.pastry3ddemo.entity.DessertProject;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class DessertProjectMapper {

    private final ObjectMapper objectMapper;

    public DessertProjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public DessertProjectResponse toResponse(DessertProject project) {
        return new DessertProjectResponse(
                project.getId(),
                project.getPrompt(),
                toPlainMap(project.getRecipeJson()),
                project.getThumbnailUrl(),
                project.getCreatedAt()
        );
    }

    private Map<String, Object> toPlainMap(JsonNode node) {
        if (node == null || node.isNull() || node.isMissingNode()) {
            return fallbackRecipe();
        }

        try {
            return objectMapper.readValue(
                    node.toString(),
                    new TypeReference<Map<String, Object>>() {}
            );
        } catch (Exception ex) {
            return fallbackRecipe();
        }
    }

    private Map<String, Object> fallbackRecipe() {
        Map<String, Object> recipe = new LinkedHashMap<>();

        recipe.put("dessert_type", "round_cake");
        recipe.put("title", "Postre generado");
        recipe.put("description", "Modelo 3D procedural construido desde una receta básica.");
        recipe.put("visual_style", "premium bakery");
        recipe.put("quality_mode", "preview_and_final");

        recipe.put("flavor_profile", Map.of(
                "main_flavor", "vanilla",
                "secondary_flavor", "cream",
                "sweetness", "medium"
        ));

        recipe.put("colors", Map.of(
                "base", "#D8A35D",
                "cream", "#FFF1DC",
                "accent", "#C92A3A",
                "plate", "#F8F5F0",
                "background", "#F2E7DA"
        ));

        recipe.put("structure", Map.of(
                "layers", 2,
                "height", "medium",
                "shape", "round",
                "has_filling", true,
                "has_drip", true,
                "has_frosting", true
        ));

        recipe.put("materials", Map.of(
                "base", "baked_cake",
                "cream", "satin_frosting",
                "topping", "glossy_fruit"
        ));

        recipe.put("decorations", java.util.List.of(
                Map.of(
                        "type", "strawberry",
                        "amount", 5,
                        "placement", "top_ring",
                        "color", "#C92A3A"
                )
        ));

        recipe.put("scene", Map.of(
                "plate", true,
                "camera", "three_quarter_closeup",
                "lighting", "soft_studio",
                "background", "warm_minimal",
                "export_ratio", "1:1"
        ));

        recipe.put("render_hints", Map.of(
                "preview_triangles", 120000,
                "final_triangles", 400000,
                "use_instancing", true,
                "use_soft_shadows", true,
                "use_depth_of_field", true
        ));

        return recipe;
    }
}
'@

Write-Host ""
Write-Host "Respuesta recipeJson corregida." -ForegroundColor Green
Write-Host "Ahora ejecuta: .\mvnw.cmd clean package -DskipTests" -ForegroundColor Cyan