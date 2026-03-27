package com.sebastian.pastry3ddemo.model.scene;

public class BoxObject extends SceneObject {

    private double width;
    private double height;
    private double depth;
    private double positionX;
    private double positionY;
    private double positionZ;
    private String color;

    public BoxObject() {
        super("box");
    }

    public BoxObject(double width, double height, double depth,
                     double positionX, double positionY, double positionZ,
                     String color) {
        super("box");
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.positionX = positionX;
        this.positionY = positionY;
        this.positionZ = positionZ;
        this.color = color;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getDepth() {
        return depth;
    }

    public void setDepth(double depth) {
        this.depth = depth;
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