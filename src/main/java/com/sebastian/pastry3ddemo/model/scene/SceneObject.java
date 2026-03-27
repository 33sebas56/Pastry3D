package com.sebastian.pastry3ddemo.model.scene;

public abstract class SceneObject {

    private String type;

    protected SceneObject() {
    }

    protected SceneObject(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}