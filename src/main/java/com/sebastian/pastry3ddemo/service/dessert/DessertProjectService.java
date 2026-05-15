package com.sebastian.pastry3ddemo.service.dessert;

import com.fasterxml.jackson.databind.JsonNode;
import com.sebastian.pastry3ddemo.dto.dessert.CreateDessertProjectRequest;
import com.sebastian.pastry3ddemo.dto.dessert.DessertProjectResponse;
import com.sebastian.pastry3ddemo.entity.AppUser;
import com.sebastian.pastry3ddemo.entity.DessertProject;
import com.sebastian.pastry3ddemo.exception.ApiException;
import com.sebastian.pastry3ddemo.mapper.DessertProjectMapper;
import com.sebastian.pastry3ddemo.repository.AppUserRepository;
import com.sebastian.pastry3ddemo.repository.DessertProjectRepository;
import com.sebastian.pastry3ddemo.service.ai.GeminiRecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class DessertProjectService {

    private final DessertProjectRepository dessertProjectRepository;
    private final AppUserRepository appUserRepository;
    private final GeminiRecipeService geminiRecipeService;
    private final DessertProjectMapper dessertProjectMapper;

    public DessertProjectService(
            DessertProjectRepository dessertProjectRepository,
            AppUserRepository appUserRepository,
            GeminiRecipeService geminiRecipeService,
            DessertProjectMapper dessertProjectMapper
    ) {
        this.dessertProjectRepository = dessertProjectRepository;
        this.appUserRepository = appUserRepository;
        this.geminiRecipeService = geminiRecipeService;
        this.dessertProjectMapper = dessertProjectMapper;
    }

    @Transactional
    public DessertProjectResponse create(UUID userId, CreateDessertProjectRequest request) {
        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "Usuario no encontrado"));

        JsonNode recipe = geminiRecipeService.generateRecipe(
                request.prompt(),
                request.visualStyle(),
                request.qualityMode()
        );

        DessertProject project = new DessertProject();
        project.setUser(user);
        project.setPrompt(request.prompt());
        project.setRecipeJson(recipe);

        DessertProject saved = dessertProjectRepository.save(project);
        return dessertProjectMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<DessertProjectResponse> findMyProjects(UUID userId) {
        return dessertProjectRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(dessertProjectMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public DessertProjectResponse findOne(UUID userId, UUID projectId) {
        DessertProject project = dessertProjectRepository.findById(projectId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Proyecto no encontrado"));

        if (!project.getUser().getId().equals(userId)) {
            throw new ApiException(HttpStatus.FORBIDDEN, "No puedes acceder a este proyecto");
        }

        return dessertProjectMapper.toResponse(project);
    }
}
