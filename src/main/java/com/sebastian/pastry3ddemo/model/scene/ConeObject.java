package com.sebastian.pastry3ddemo.model.scene;

public class ConeObject extends SceneObject {

    private double radius;
    private double height;
    private double positionX;
    private double positionY;
    private double positionZ;
    private String color;

    public ConeObject() {
        super("cone");
    }

    public ConeObject(double radius, double height,
                      double positionX, double positionY, double positionZ,
                      String color) {
        super("cone");
        this.radius = radius;
        this.height = height;
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

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
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