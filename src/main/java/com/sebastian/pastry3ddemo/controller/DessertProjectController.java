package com.sebastian.pastry3ddemo.controller;

import com.sebastian.pastry3ddemo.dto.dessert.CreateDessertProjectRequest;
import com.sebastian.pastry3ddemo.dto.dessert.DessertProjectResponse;
import com.sebastian.pastry3ddemo.security.UserPrincipal;
import com.sebastian.pastry3ddemo.service.dessert.DessertProjectService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/dessert-projects")
public class DessertProjectController {

    private final DessertProjectService dessertProjectService;

    public DessertProjectController(DessertProjectService dessertProjectService) {
        this.dessertProjectService = dessertProjectService;
    }

    @PostMapping
    public DessertProjectResponse create(
            @AuthenticationPrincipal UserPrincipal user,
            @Valid @RequestBody CreateDessertProjectRequest request
    ) {
        return dessertProjectService.create(user.getId(), request);
    }

    @GetMapping
    public List<DessertProjectResponse> findMyProjects(
            @AuthenticationPrincipal UserPrincipal user
    ) {
        return dessertProjectService.findMyProjects(user.getId());
    }

    @GetMapping("/{id}")
    public DessertProjectResponse findOne(
            @AuthenticationPrincipal UserPrincipal user,
            @PathVariable UUID id
    ) {
        return dessertProjectService.findOne(user.getId(), id);
    }
}
