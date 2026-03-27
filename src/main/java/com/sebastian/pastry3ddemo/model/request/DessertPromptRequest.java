package com.sebastian.pastry3ddemo.model.request;

import jakarta.validation.constraints.NotBlank;

public class DessertPromptRequest {

    @NotBlank(message = "El prompt no puede estar vacio")
    private String prompt;

    public DessertPromptRequest() {
    }

    public DessertPromptRequest(String prompt) {
        this.prompt = prompt;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}