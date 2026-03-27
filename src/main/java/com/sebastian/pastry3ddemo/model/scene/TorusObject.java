package com.sebastian.pastry3ddemo.model.scene;

public class TorusObject extends SceneObject {

    private double radius;
    private double tube;
    private double positionX;
    private double positionY;
    private double positionZ;
    private double rotationX;
    private double rotationY;
    private double rotationZ;
    private String color;

    public TorusObject() {
        super("torus");
    }

    public TorusObject(double radius, double tube,
                       double positionX, double positionY, double positionZ,
                       double rotationX, double rotationY, double rotationZ,
                       String color) {
        super("torus");
        this.radius = radius;
        this.tube = tube;
        this.positionX = positionX;
        this.positionY = positionY;
        this.positionZ = positionZ;
        this.rotationX = rotationX;
        this.rotationY = rotationY;
        this.rotationZ = rotationZ;
        this.color = color;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getTube() {
        return tube;
    }

    public void setTube(double tube) {
        this.tube = tube;
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

    public double getRotationX() {
        return rotationX;
    }

    public void setRotationX(double rotationX) {
        this.rotationX = rotationX;
    }

    public double getRotationY() {
        return rotationY;
    }

    public void setRotationY(double rotationY) {
        this.rotationY = rotationY;
    }

    public double getRotationZ() {
        return rotationZ;
    }

    public void setRotationZ(double rotationZ) {
        this.rotationZ = rotationZ;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}