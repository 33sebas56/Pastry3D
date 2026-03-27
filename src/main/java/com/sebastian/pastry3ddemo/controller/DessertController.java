package com.sebastian.pastry3ddemo.controller;

import com.sebastian.pastry3ddemo.model.request.DessertPromptRequest;
import com.sebastian.pastry3ddemo.model.response.DessertGenerationResponse;
import com.sebastian.pastry3ddemo.service.DessertGenerationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/desserts")
@CrossOrigin(origins = "*")
public class DessertController {

    private final DessertGenerationService dessertGenerationService;

    public DessertController(DessertGenerationService dessertGenerationService) {
        this.dessertGenerationService = dessertGenerationService;
    }

    @PostMapping("/generate")
    public ResponseEntity<DessertGenerationResponse> generateDessert(
            @Valid @RequestBody DessertPromptRequest request) {

        DessertGenerationResponse response =
                dessertGenerationService.generateFromPrompt(request.getPrompt());

        return ResponseEntity.ok(response);
    }
}