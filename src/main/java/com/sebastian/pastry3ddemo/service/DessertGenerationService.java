package com.sebastian.pastry3ddemo.service;

import com.sebastian.pastry3ddemo.model.ai.DessertInterpretation;
import com.sebastian.pastry3ddemo.model.response.DessertGenerationResponse;
import com.sebastian.pastry3ddemo.model.scene.Scene3D;
import org.springframework.stereotype.Service;

@Service
public class DessertGenerationService {

    private final AiInterpreterService aiInterpreterService;
    private final SceneBuilderService sceneBuilderService;

    public DessertGenerationService(AiInterpreterService aiInterpreterService,
                                    SceneBuilderService sceneBuilderService) {
        this.aiInterpreterService = aiInterpreterService;
        this.sceneBuilderService = sceneBuilderService;
    }

    public DessertGenerationResponse generateFromPrompt(String prompt) {
        DessertInterpretation interpretation = aiInterpreterService.interpretPrompt(prompt);
        Scene3D scene = sceneBuilderService.buildScene(interpretation);

        String description = buildDescription(interpretation);

        DessertGenerationResponse response = new DessertGenerationResponse();
        response.setDessertName(interpretation.getDessertName());
        response.setDescription(description);
        response.setIngredients(interpretation.getIngredients());
        response.setSteps(interpretation.getSteps());
        response.setScene(scene);

        return response;
    }

    private String buildDescription(DessertInterpretation interpretation) {
        return "Se generó una representación 3D simple de " + interpretation.getDessertName()
                + ", con " + interpretation.getTiers() + " piso(s), sabor "
                + interpretation.getFlavor() + ", cobertura "
                + interpretation.getCovering() + " y decoración básica.";
    }
}