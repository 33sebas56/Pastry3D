package com.sebastian.pastry3ddemo.model.scene;

public class SphereObject extends SceneObject {

    private double radius;
    private double positionX;
    private double positionY;
    private double positionZ;
    private String color;

    public SphereObject() {
        super("sphere");
    }

    public SphereObject(double radius, double positionX, double positionY, double positionZ, String color) {
        super("sphere");
        this.radius = radius;
        this.positionX = positionX;
        this.positionY = positionY;
        this.positionZ = positionZ;
        this.color = color;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public double getPositionZ() {
        return positionZ;
    }

    public void setPositionZ(double positionZ) {
        this.positionZ = positionZ;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}