package com.sebastian.pastry3ddemo.model.scene;

public class CylinderObject extends SceneObject {

    private double radiusTop;
    private double radiusBottom;
    private double height;
    private double positionX;
    private double positionY;
    private double positionZ;
    private String color;

    public CylinderObject() {
        super("cylinder");
    }

    public CylinderObject(double radiusTop, double radiusBottom, double height,
                          double positionX, double positionY, double positionZ, String color) {
        super("cylinder");
        this.radiusTop = radiusTop;
        this.radiusBottom = radiusBottom;
        this.height = height;
        this.positionX = positionX;
        this.positionY = positionY;
        this.positionZ = positionZ;
        this.color = color;
    }

    public double getRadiusTop() {
        return radiusTop;
    }

    public void setRadiusTop(double radiusTop) {
        this.radiusTop = radiusTop;
    }

    public double getRadiusBottom() {
        return radiusBottom;
    }

    public void setRadiusBottom(double radiusBottom) {
        this.radiusBottom = radiusBottom;
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