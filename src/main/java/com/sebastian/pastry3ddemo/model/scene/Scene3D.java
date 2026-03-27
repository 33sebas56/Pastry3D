package com.sebastian.pastry3ddemo.model.scene;

import java.util.ArrayList;
import java.util.List;

public class Scene3D {

    private List<SceneObject> objects;

    public Scene3D() {
        this.objects = new ArrayList<>();
    }

    public Scene3D(List<SceneObject> objects) {
        this.objects = objects;
    }

    public List<SceneObject> getObjects() {
        return objects;
    }

    public void setObjects(List<SceneObject> objects) {
        this.objects = objects;
    }
}