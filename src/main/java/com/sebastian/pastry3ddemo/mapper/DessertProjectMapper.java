package com.sebastian.pastry3ddemo.mapper;

import com.sebastian.pastry3ddemo.dto.dessert.DessertProjectResponse;
import com.sebastian.pastry3ddemo.entity.DessertProject;
import org.springframework.stereotype.Component;

@Component
public class DessertProjectMapper {

    public DessertProjectResponse toResponse(DessertProject project) {
        return new DessertProjectResponse(
                project.getId(),
                project.getPrompt(),
                project.getRecipeJson(),
                project.getThumbnailUrl(),
                project.getCreatedAt()
        );
    }
}
