package com.sebastian.pastry3ddemo.service;

import com.sebastian.pastry3ddemo.model.ai.DessertInterpretation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AiInterpreterService {

    public DessertInterpretation interpretPrompt(String prompt) {
        String text = prompt == null ? "" : prompt.toLowerCase();

        DessertInterpretation interpretation = new DessertInterpretation();

        interpretation.setDessertType("cake");
        interpretation.setDessertName("Pastel personalizado");
        interpretation.setShape("cylinder");
        interpretation.setFlavor("vainilla");
        interpretation.setCovering("sin cobertura especial");
        interpretation.setPrimaryColor("#e7c8a0");
        interpretation.setSecondaryColor("#f5efe6");
        interpretation.setTiers(extractTiers(text));

        List<String> toppings = new ArrayList<>();
        List<String> decorations = new ArrayList<>();
        List<String> ingredients = new ArrayList<>();
        List<String> steps = new ArrayList<>();

        detectDessertType(text, interpretation);
        detectFlavor(text, interpretation, ingredients);
        detectCovering(text, interpretation, ingredients);
        detectToppings(text, toppings, ingredients);
        detectDecorations(text, decorations, ingredients);

        buildCommonIngredients(interpretation, ingredients);
        buildSteps(interpretation, toppings, decorations, steps);

        interpretation.setToppings(removeDuplicates(toppings));
        interpretation.setDecorations(removeDuplicates(decorations));
        interpretation.setIngredients(removeDuplicates(ingredients));
        interpretation.setSteps(steps);

        return interpretation;
    }

    private void detectDessertType(String text, DessertInterpretation interpretation) {
        if (text.contains("cupcake")) {
            interpretation.setDessertType("cupcake");
            interpretation.setDessertName("Cupcake decorado");
            interpretation.setShape("cupcake");
            interpretation.setTiers(1);
            return;
        }

        if (text.contains("dona") || text.contains("donut")) {
            interpretation.setDessertType("donut");
            interpretation.setDessertName("Dona glaseada");
            interpretation.setShape("torus");
            interpretation.setTiers(1);
            return;
        }

        if (text.contains("cheesecake")) {
            interpretation.setDessertType("cheesecake");
            interpretation.setDessertName("Cheesecake");
            interpretation.setShape("cylinder");
            interpretation.setTiers(1);
            return;
        }

        if (text.contains("milhojas") || text.contains("mil hojas") || text.contains("millefeuille")) {
            interpretation.setDessertType("millefeuille");
            interpretation.setDessertName("Milhojas");
            interpretation.setShape("box_layers");
            interpretation.setTiers(1);
            return;
        }

        if (text.contains("brownie")) {
            interpretation.setDessertType("brownie");
            interpretation.setDessertName("Brownie");
            interpretation.setShape("box");
            interpretation.setTiers(1);
            return;
        }

        if (text.contains("galleta") || text.contains("cookie")) {
            interpretation.setDessertType("cookie");
            interpretation.setDessertName("Galleta");
            interpretation.setShape("cookie");
            interpretation.setTiers(1);
            return;
        }

        if (text.contains("flan")) {
            interpretation.setDessertType("flan");
            interpretation.setDessertName("Flan");
            interpretation.setShape("flan");
            interpretation.setTiers(1);
            return;
        }

        if (text.contains("muffin")) {
            interpretation.setDessertType("muffin");
            interpretation.setDessertName("Muffin");
            interpretation.setShape("muffin");
            interpretation.setTiers(1);
            return;
        }

        if (text.contains("tarta")) {
            interpretation.setDessertType("tart");
            interpretation.setDessertName("Tarta");
            interpretation.setShape("cylinder");
            interpretation.setTiers(1);
            return;
        }

        interpretation.setDessertType("cake");
        interpretation.setDessertName("Pastel");
        interpretation.setShape("cylinder");
    }

    private void detectFlavor(String text, DessertInterpretation interpretation, List<String> ingredients) {
        if (text.contains("red velvet")) {
            interpretation.setFlavor("red velvet");
            interpretation.setPrimaryColor("#9b1c31");
            interpretation.setSecondaryColor("#fff5f5");
            ingredients.add("cacao en polvo");
            ingredients.add("colorante rojo");
            return;
        }

        if (text.contains("chocolate")) {
            interpretation.setFlavor("chocolate");
            interpretation.setPrimaryColor("#5a3825");
            interpretation.setSecondaryColor("#7a4a2e");
            ingredients.add("cacao en polvo");
            return;
        }

        if (text.contains("vainilla")) {
            interpretation.setFlavor("vainilla");
            interpretation.setPrimaryColor("#e7c8a0");
            interpretation.setSecondaryColor("#f4dec2");
            ingredients.add("vainilla");
            return;
        }

        if (text.contains("fresa")) {
            interpretation.setFlavor("fresa");
            interpretation.setPrimaryColor("#f78da7");
            interpretation.setSecondaryColor("#ffd6e0");
            ingredients.add("fresas");
            return;
        }

        if (text.contains("limon") || text.contains("limón")) {
            interpretation.setFlavor("limon");
            interpretation.setPrimaryColor("#f3e37c");
            interpretation.setSecondaryColor("#fff9c4");
            ingredients.add("limon");
            return;
        }

        if (text.contains("pistacho")) {
            interpretation.setFlavor("pistacho");
            interpretation.setPrimaryColor("#8fbf6a");
            interpretation.setSecondaryColor("#dcecc9");
            ingredients.add("pistacho");
            return;
        }

        if (text.contains("cafe") || text.contains("café")) {
            interpretation.setFlavor("cafe");
            interpretation.setPrimaryColor("#6f4e37");
            interpretation.setSecondaryColor("#a67b5b");
            ingredients.add("cafe");
            return;
        }

        if (text.contains("coco")) {
            interpretation.setFlavor("coco");
            interpretation.setPrimaryColor("#f5f1e8");
            interpretation.setSecondaryColor("#fffaf2");
            ingredients.add("coco rallado");
        }
    }

    private void detectCovering(String text, DessertInterpretation interpretation, List<String> ingredients) {
        if (text.contains("crema blanca") || text.contains("chantilly")) {
            interpretation.setCovering("crema blanca");
            interpretation.setSecondaryColor("#fffaf2");
            ingredients.add("crema para batir");
            return;
        }

        if (text.contains("ganache")) {
            interpretation.setCovering("ganache");
            interpretation.setSecondaryColor("#3b2418");
            ingredients.add("chocolate para cobertura");
            ingredients.add("crema para batir");
            return;
        }

        if (text.contains("fondant")) {
            interpretation.setCovering("fondant");
            interpretation.setSecondaryColor("#f3f4f6");
            ingredients.add("fondant");
            return;
        }

        if (text.contains("glaseado rosa")) {
            interpretation.setCovering("glaseado rosa");
            interpretation.setSecondaryColor("#f9a8d4");
            ingredients.add("azucar glas");
            return;
        }

        if (text.contains("caramelo")) {
            interpretation.setCovering("caramelo");
            interpretation.setSecondaryColor("#c68b3c");
            ingredients.add("azucar");
        }
    }

    private void detectToppings(String text, List<String> toppings, List<String> ingredients) {
        if (text.contains("fresas")) {
            toppings.add("strawberries");
            ingredients.add("fresas");
        }

        if (text.contains("cereza") || text.contains("cerezas")) {
            toppings.add("cherries");
            ingredients.add("cerezas");
        }

        if (text.contains("arandano") || text.contains("arándano") || text.contains("arándanos")) {
            toppings.add("blueberries");
            ingredients.add("arándanos");
        }

        if (text.contains("macaron") || text.contains("macarons")) {
            toppings.add("macarons");
            ingredients.add("harina de almendra");
        }

        if (text.contains("galleta") || text.contains("galletitas")) {
            toppings.add("mini_cookies");
            ingredients.add("galletas");
        }

        if (text.contains("rosa") || text.contains("rosa arriba")) {
            toppings.add("rose");
        }
    }

    private void detectDecorations(String text, List<String> decorations, List<String> ingredients) {
        if (text.contains("rosa") || text.contains("rosas")) {
            decorations.add("rose");
        }

        if (text.contains("vela") || text.contains("vela arriba") || text.contains("velas")) {
            decorations.add("candles");
        }

        if (text.contains("crema batida") || text.contains("chantilly")) {
            decorations.add("whipped_cream");
            ingredients.add("crema para batir");
        }

        if (text.contains("chispas")) {
            decorations.add("sprinkles");
            ingredients.add("chispas de colores");
        }

        if (text.contains("corazon") || text.contains("corazón") || text.contains("corazones")) {
            decorations.add("hearts");
        }

        if (text.contains("estrella") || text.contains("estrellas")) {
            decorations.add("stars");
        }

        if (text.contains("hojas") || text.contains("hoja verde")) {
            decorations.add("leaves");
        }

        if (text.contains("perlas")) {
            decorations.add("pearls");
        }

        if (text.contains("salsa cayendo") || text.contains("drip") || text.contains("chorreado")) {
            decorations.add("drip");
        }
    }

    private void buildCommonIngredients(DessertInterpretation interpretation, List<String> ingredients) {
        switch (interpretation.getDessertType()) {
            case "cupcake":
            case "cake":
            case "muffin":
            case "tart":
                ingredients.add("harina");
                ingredients.add("azucar");
                ingredients.add("huevos");
                ingredients.add("mantequilla");
                ingredients.add("leche");
                break;
            case "donut":
                ingredients.add("harina");
                ingredients.add("azucar");
                ingredients.add("huevos");
                ingredients.add("mantequilla");
                ingredients.add("levadura");
                break;
            case "cheesecake":
                ingredients.add("queso crema");
                ingredients.add("azucar");
                ingredients.add("huevos");
                ingredients.add("galletas");
                ingredients.add("mantequilla");
                break;
            case "millefeuille":
                ingredients.add("masa de hojaldre");
                ingredients.add("crema pastelera");
                ingredients.add("azucar glas");
                break;
            case "brownie":
                ingredients.add("harina");
                ingredients.add("azucar");
                ingredients.add("huevos");
                ingredients.add("mantequilla");
                ingredients.add("chocolate");
                break;
            case "cookie":
                ingredients.add("harina");
                ingredients.add("azucar");
                ingredients.add("mantequilla");
                ingredients.add("huevo");
                break;
            case "flan":
                ingredients.add("leche condensada");
                ingredients.add("leche evaporada");
                ingredients.add("huevos");
                ingredients.add("azucar");
                break;
            default:
                ingredients.add("harina");
                ingredients.add("azucar");
                ingredients.add("huevos");
                ingredients.add("mantequilla");
        }
    }

    private void buildSteps(DessertInterpretation interpretation,
                            List<String> toppings,
                            List<String> decorations,
                            List<String> steps) {
        steps.add("Preparar la base del postre tipo " + interpretation.getDessertType() + ".");
        steps.add("Incorporar el sabor principal de " + interpretation.getFlavor() + ".");
        steps.add("Hornear o enfriar según el tipo de postre.");
        steps.add("Aplicar la cobertura de " + interpretation.getCovering() + ".");

        if (!toppings.isEmpty()) {
            steps.add("Añadir toppings: " + String.join(", ", toppings) + ".");
        }

        if (!decorations.isEmpty()) {
            steps.add("Decorar con: " + String.join(", ", decorations) + ".");
        }

        steps.add("Presentar el postre final.");
    }

    private int extractTiers(String text) {
        if (text.contains("tres pisos") || text.contains("3 pisos")) {
            return 3;
        }
        if (text.contains("dos pisos") || text.contains("2 pisos")) {
            return 2;
        }
        return 1;
    }

    private List<String> removeDuplicates(List<String> source) {
        List<String> result = new ArrayList<>();
        for (String item : source) {
            if (!result.contains(item)) {
                result.add(item);
            }
        }
        return result;
    }
}