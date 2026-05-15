package com.sebastian.pastry3ddemo.dto.dessert;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateDessertProjectRequest(
        @NotBlank(message = "La idea del postre es obligatoria")
        @Size(min = 3, max = 600, message = "La idea debe tener entre 3 y 600 caracteres")
        String prompt,

        @Size(max = 80, message = "El estilo visual no puede superar 80 caracteres")
        String visualStyle,

        @Size(max = 30, message = "El modo de calidad no puede superar 30 caracteres")
        String qualityMode
) {}
