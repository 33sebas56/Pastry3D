package com.sebastian.pastry3ddemo.model.ai;

import java.util.ArrayList;
import java.util.List;

public class DessertInterpretation {

    private String dessertName;
    private String dessertType;
    private String shape;
    private int tiers;
    private String flavor;
    private String covering;
    private String primaryColor;
    private String secondaryColor;
    private List<String> toppings;
    private List<String> decorations;
    private List<String> ingredients;
    private List<String> steps;

    public DessertInterpretation() {
        this.toppings = new ArrayList<>();
        this.decorations = new ArrayList<>();
        this.ingredients = new ArrayList<>();
        this.steps = new ArrayList<>();
        this.tiers = 1;
    }

    public String getDessertName() {
        return dessertName;
    }

    public void setDessertName(String dessertName) {
        this.dessertName = dessertName;
    }

    public String getDessertType() {
        return dessertType;
    }

    public void setDessertType(String dessertType) {
        this.dessertType = dessertType;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public int getTiers() {
        return tiers;
    }

    public void setTiers(int tiers) {
        this.tiers = tiers;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public String getCovering() {
        return covering;
    }

    public void setCovering(String covering) {
        this.covering = covering;
    }

    public String getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
    }

    public String getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(String secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public List<String> getToppings() {
        return toppings;
    }

    public void setToppings(List<String> toppings) {
        this.toppings = toppings;
    }

    public List<String> getDecorations() {
        return decorations;
    }

    public void setDecorations(List<String> decorations) {
        this.decorations = decorations;
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
}