package com.sebastian.pastry3ddemo.service;

import com.sebastian.pastry3ddemo.model.ai.DessertInterpretation;
import com.sebastian.pastry3ddemo.model.scene.BoxObject;
import com.sebastian.pastry3ddemo.model.scene.ConeObject;
import com.sebastian.pastry3ddemo.model.scene.CylinderObject;
import com.sebastian.pastry3ddemo.model.scene.Scene3D;
import com.sebastian.pastry3ddemo.model.scene.SceneObject;
import com.sebastian.pastry3ddemo.model.scene.SphereObject;
import com.sebastian.pastry3ddemo.model.scene.TorusObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SceneBuilderService {

    public Scene3D buildScene(DessertInterpretation interpretation) {
        List<SceneObject> objects = new ArrayList<>();

        switch (interpretation.getDessertType()) {
            case "donut":
                buildDonut(objects, interpretation);
                break;
            case "cupcake":
                buildCupcake(objects, interpretation);
                break;
            case "cheesecake":
                buildCheesecake(objects, interpretation);
                break;
            case "millefeuille":
                buildMillefeuille(objects, interpretation);
                break;
            case "brownie":
                buildBrownie(objects, interpretation);
                break;
            case "cookie":
                buildCookie(objects, interpretation);
                break;
            case "flan":
                buildFlan(objects, interpretation);
                break;
            case "muffin":
                buildMuffin(objects, interpretation);
                break;
            case "tart":
                buildTart(objects, interpretation);
                break;
            case "cake":
            default:
                buildCake(objects, interpretation);
                break;
        }

        addToppings(objects, interpretation);
        addDecorations(objects, interpretation);

        return new Scene3D(objects);
    }

    private void buildCake(List<SceneObject> objects, DessertInterpretation interpretation) {
        double currentBaseY = 0.0;
        int tiers = interpretation.getTiers();

        for (int i = 0; i < tiers; i++) {
            double radius = 4.0 - (i * 0.8);
            double height = 1.8;
            double centerY = currentBaseY + (height / 2.0);

            objects.add(new CylinderObject(
                    radius * 0.92,
                    radius,
                    height,
                    0.0,
                    centerY,
                    0.0,
                    resolveTierColor(interpretation, i)
            ));

            currentBaseY += height;
        }
    }

    private void buildCupcake(List<SceneObject> objects, DessertInterpretation interpretation) {
        objects.add(new CylinderObject(2.3, 2.9, 2.4, 0, 1.2, 0, "#b97745"));
        objects.add(new SphereObject(2.2, 0, 3.0, 0, interpretation.getSecondaryColor()));
    }

    private void buildDonut(List<SceneObject> objects, DessertInterpretation interpretation) {
        objects.add(new TorusObject(
                3.0, 1.2,
                0.0, 1.5, 0.0,
                Math.PI / 2, 0.0, 0.0,
                interpretation.getPrimaryColor()
        ));
        objects.add(new TorusObject(
                3.05, 0.55,
                0.0, 1.85, 0.0,
                Math.PI / 2, 0.0, 0.0,
                interpretation.getSecondaryColor()
        ));
    }

    private void buildCheesecake(List<SceneObject> objects, DessertInterpretation interpretation) {
        objects.add(new CylinderObject(4.0, 4.2, 1.0, 0, 0.5, 0, "#d6b37a"));
        objects.add(new CylinderObject(3.8, 4.0, 2.0, 0, 2.0, 0, "#fff4d6"));
    }

    private void buildMillefeuille(List<SceneObject> objects, DessertInterpretation interpretation) {
        double y = 0.35;
        for (int i = 0; i < 5; i++) {
            objects.add(new BoxObject(6.0, 0.35, 3.2, 0, y, 0, "#d9a45b"));
            y += 0.4;
            if (i < 4) {
                objects.add(new BoxObject(5.8, 0.22, 3.0, 0, y, 0, "#fff5dc"));
                y += 0.28;
            }
        }
    }

    private void buildBrownie(List<SceneObject> objects, DessertInterpretation interpretation) {
        objects.add(new BoxObject(5.5, 1.8, 5.5, 0, 0.9, 0, "#4a2c1d"));
    }

    private void buildCookie(List<SceneObject> objects, DessertInterpretation interpretation) {
        objects.add(new CylinderObject(3.5, 3.7, 0.8, 0, 0.4, 0, "#c9965b"));
    }

    private void buildFlan(List<SceneObject> objects, DessertInterpretation interpretation) {
        objects.add(new CylinderObject(3.2, 4.0, 2.2, 0, 1.1, 0, "#f3d28a"));
        objects.add(new SphereObject(2.5, 0, 2.4, 0, "#c68b3c"));
    }

    private void buildMuffin(List<SceneObject> objects, DessertInterpretation interpretation) {
        objects.add(new CylinderObject(2.5, 3.2, 2.0, 0, 1.0, 0, "#a86c3d"));
        objects.add(new SphereObject(2.3, 0, 2.8, 0, interpretation.getPrimaryColor()));
    }

    private void buildTart(List<SceneObject> objects, DessertInterpretation interpretation) {
        objects.add(new CylinderObject(4.2, 4.6, 1.0, 0, 0.5, 0, "#d2a56d"));
        objects.add(new CylinderObject(4.0, 4.1, 1.0, 0, 1.4, 0, interpretation.getSecondaryColor()));
    }

    private void addToppings(List<SceneObject> objects, DessertInterpretation interpretation) {
        List<String> toppings = interpretation.getToppings();
        if (toppings == null) {
            return;
        }

        double topY = findTopY(interpretation);

        if (toppings.contains("strawberries")) {
            objects.add(new SphereObject(0.28, -0.8, topY, 0.3, "#d90429"));
            objects.add(new SphereObject(0.28, 0.0, topY + 0.05, -0.2, "#ef233c"));
            objects.add(new SphereObject(0.28, 0.8, topY, 0.2, "#d90429"));
        }

        if (toppings.contains("cherries")) {
            objects.add(new SphereObject(0.22, -0.5, topY + 0.05, 0.0, "#8b0000"));
            objects.add(new SphereObject(0.22, 0.5, topY + 0.05, 0.2, "#8b0000"));
        }

        if (toppings.contains("blueberries")) {
            objects.add(new SphereObject(0.18, -0.6, topY, -0.3, "#304ffe"));
            objects.add(new SphereObject(0.18, 0.0, topY + 0.04, 0.1, "#3d5afe"));
            objects.add(new SphereObject(0.18, 0.6, topY, 0.3, "#304ffe"));
        }

        if (toppings.contains("mini_cookies")) {
            objects.add(new CylinderObject(0.55, 0.55, 0.18, -0.9, topY, 0.0, "#c9965b"));
            objects.add(new CylinderObject(0.55, 0.55, 0.18, 0.9, topY, 0.2, "#c9965b"));
        }

        if (toppings.contains("macarons")) {
            objects.add(new SphereObject(0.35, -1.0, topY, 0.0, "#f9a8d4"));
            objects.add(new SphereObject(0.35, 1.0, topY, 0.1, "#c4b5fd"));
        }

        if (toppings.contains("rose")) {
            addRose(objects, 0.0, topY + 0.1, 0.0, "#f43f5e");
        }
    }

    private void addDecorations(List<SceneObject> objects, DessertInterpretation interpretation) {
        List<String> decorations = interpretation.getDecorations();
        if (decorations == null) {
            return;
        }

        double topY = findTopY(interpretation);

        if (decorations.contains("candles")) {
            objects.add(new CylinderObject(0.12, 0.12, 1.0, -0.6, topY + 0.5, 0.0, "#60a5fa"));
            objects.add(new SphereObject(0.10, -0.6, topY + 1.05, 0.0, "#f59e0b"));
            objects.add(new CylinderObject(0.12, 0.12, 1.0, 0.6, topY + 0.5, 0.1, "#34d399"));
            objects.add(new SphereObject(0.10, 0.6, topY + 1.05, 0.1, "#f59e0b"));
        }

        if (decorations.contains("whipped_cream")) {
            objects.add(new ConeObject(0.45, 0.7, -1.2, topY + 0.25, 0.0, "#fffaf2"));
            objects.add(new ConeObject(0.45, 0.7, 0.0, topY + 0.3, -0.8, "#fffaf2"));
            objects.add(new ConeObject(0.45, 0.7, 1.2, topY + 0.25, 0.2, "#fffaf2"));
        }

        if (decorations.contains("rose")) {
            addRose(objects, -1.1, topY + 0.1, -0.5, "#ec4899");
            addRose(objects, 1.1, topY + 0.1, 0.5, "#fb7185");
        }

        if (decorations.contains("leaves")) {
            objects.add(new SphereObject(0.16, -0.2, topY + 0.05, -0.8, "#16a34a"));
            objects.add(new SphereObject(0.16, 0.2, topY + 0.05, 0.8, "#16a34a"));
        }

        if (decorations.contains("pearls")) {
            objects.add(new SphereObject(0.10, -1.2, topY, 0.0, "#f8fafc"));
            objects.add(new SphereObject(0.10, -0.4, topY + 0.02, 0.5, "#f8fafc"));
            objects.add(new SphereObject(0.10, 0.4, topY + 0.02, -0.5, "#f8fafc"));
            objects.add(new SphereObject(0.10, 1.2, topY, 0.0, "#f8fafc"));
        }

        if (decorations.contains("hearts")) {
            objects.add(new SphereObject(0.18, -0.3, topY + 0.12, 0.0, "#ff4d6d"));
            objects.add(new SphereObject(0.18, 0.0, topY + 0.12, 0.0, "#ff4d6d"));
            objects.add(new ConeObject(0.22, 0.35, -0.15, topY - 0.02, 0.0, "#ff4d6d"));
        }

        if (decorations.contains("stars")) {
            objects.add(new SphereObject(0.14, -0.8, topY + 0.08, -0.4, "#ffd166"));
            objects.add(new SphereObject(0.14, 0.8, topY + 0.08, 0.4, "#ffd166"));
        }

        if (decorations.contains("drip")) {
            objects.add(new CylinderObject(0.18, 0.18, 0.8, -2.0, topY - 0.2, 0.0, interpretation.getSecondaryColor()));
            objects.add(new CylinderObject(0.18, 0.18, 0.6, 0.0, topY - 0.1, 2.0, interpretation.getSecondaryColor()));
            objects.add(new CylinderObject(0.18, 0.18, 0.7, 2.0, topY - 0.15, -0.5, interpretation.getSecondaryColor()));
        }

        if (decorations.contains("sprinkles")) {
            objects.add(new SphereObject(0.08, -0.9, topY + 0.03, 0.0, "#ff006e"));
            objects.add(new SphereObject(0.08, -0.3, topY + 0.04, 0.6, "#8338ec"));
            objects.add(new SphereObject(0.08, 0.3, topY + 0.04, -0.6, "#3a86ff"));
            objects.add(new SphereObject(0.08, 0.9, topY + 0.03, 0.1, "#ffbe0b"));
        }
    }

    private void addRose(List<SceneObject> objects, double x, double y, double z, String color) {
        objects.add(new SphereObject(0.18, x, y, z, color));
        objects.add(new SphereObject(0.14, x - 0.18, y + 0.02, z, color));
        objects.add(new SphereObject(0.14, x + 0.18, y + 0.02, z, color));
        objects.add(new SphereObject(0.14, x, y + 0.02, z - 0.18, color));
        objects.add(new SphereObject(0.14, x, y + 0.02, z + 0.18, color));
    }

    private double findTopY(DessertInterpretation interpretation) {
        switch (interpretation.getDessertType()) {
            case "donut":
                return 2.2;
            case "cupcake":
            case "muffin":
                return 4.5;
            case "cheesecake":
                return 3.1;
            case "millefeuille":
                return 3.4;
            case "brownie":
                return 1.9;
            case "cookie":
                return 1.0;
            case "flan":
                return 4.0;
            case "tart":
                return 2.3;
            case "cake":
            default:
                return interpretation.getTiers() * 1.8 + 0.5;
        }
    }

    private String resolveTierColor(DessertInterpretation interpretation, int tierIndex) {
        if ("crema blanca".equalsIgnoreCase(interpretation.getCovering())
                || "fondant".equalsIgnoreCase(interpretation.getCovering())
                || "glaseado rosa".equalsIgnoreCase(interpretation.getCovering())
                || "ganache".equalsIgnoreCase(interpretation.getCovering())
                || "caramelo".equalsIgnoreCase(interpretation.getCovering())) {
            if (tierIndex == 0) {
                return interpretation.getSecondaryColor();
            }
            return interpretation.getPrimaryColor();
        }

        return tierIndex == 0 ? interpretation.getPrimaryColor() : interpretation.getSecondaryColor();
    }
}