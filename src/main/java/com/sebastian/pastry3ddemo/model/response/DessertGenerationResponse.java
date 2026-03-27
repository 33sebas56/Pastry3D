package com.sebastian.pastry3ddemo.model.response;

import com.sebastian.pastry3ddemo.model.scene.Scene3D;

import java.util.ArrayList;
import java.util.List;

public class DessertGenerationResponse {

    private String dessertName;
    private String description;
    private List<String> ingredients;
    private List<String> steps;
    private Scene3D scene;

    public DessertGenerationResponse() {
        this.ingredients = new ArrayList<>();
        this.steps = new ArrayList<>();
    }

    public DessertGenerationResponse(String dessertName, String description, List<String> ingredients,
                                     List<String> steps, Scene3D scene) {
        this.dessertName = dessertName;
        this.description = description;
        this.ingredients = ingredients;
        this.steps = steps;
        this.scene = scene;
    }

    public String getDessertName() {
        return dessertName;
    }

    public void setDessertName(String dessertName) {
        this.dessertName = dessertName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public Scene3D getScene() {
        return scene;
    }

    public void setScene(Scene3D scene) {
        this.scene = scene;
    }
}