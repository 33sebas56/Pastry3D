package com.sebastian.pastry3ddemo.service.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebastian.pastry3ddemo.config.GeminiConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GeminiRecipeService {

    private static final String GEMINI_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/{model}:generateContent";

    private final GeminiConfig geminiConfig;
    private final ObjectMapper objectMapper;
    private final RestClient restClient;

    public GeminiRecipeService(GeminiConfig geminiConfig, ObjectMapper objectMapper) {
        this.geminiConfig = geminiConfig;
        this.objectMapper = objectMapper;
        this.restClient = RestClient.builder().build();
    }

    public JsonNode generateRecipe(String userPrompt, String visualStyle, String qualityMode) {
        if (geminiConfig.getApiKey() == null || geminiConfig.getApiKey().isBlank()) {
            return fallbackRecipe(userPrompt, visualStyle, qualityMode);
        }

        try {
            String prompt = buildPrompt(userPrompt, visualStyle, qualityMode);

            JsonNode requestBody = objectMapper.createObjectNode()
                    .set("contents", objectMapper.createArrayNode()
                            .add(objectMapper.createObjectNode()
                                    .set("parts", objectMapper.createArrayNode()
                                            .add(objectMapper.createObjectNode()
                                                    .put("text", prompt)
                                            )
                                    )
                            )
                    );

            JsonNode response = restClient.post()
                    .uri(GEMINI_URL, geminiConfig.getModel())
                    .header("x-goog-api-key", geminiConfig.getApiKey())
                    .body(requestBody)
                    .retrieve()
                    .body(JsonNode.class);

            String text = response
                    .path("candidates")
                    .path(0)
                    .path("content")
                    .path("parts")
                    .path(0)
                    .path("text")
                    .asText();

            String jsonText = extractJson(text);
            JsonNode recipe = objectMapper.readTree(jsonText);

            if (!recipe.hasNonNull("dessert_type")) {
                return fallbackRecipe(userPrompt, visualStyle, qualityMode);
            }

            return recipe;
        } catch (Exception ex) {
            return fallbackRecipe(userPrompt, visualStyle, qualityMode);
        }
    }

    private String buildPrompt(String userPrompt, String visualStyle, String qualityMode) {
        String style = cleanOrDefault(visualStyle, "premium bakery");
        String quality = cleanOrDefault(qualityMode, "preview_and_final");

        return """
        You are an AI pastry 3D recipe generator.

        Your job is NOT to create 3D code.
        Your job is NOT to generate mesh vertices.
        Your job is to convert a user's dessert idea into a strict JSON recipe for a lightweight procedural 3D pastry renderer.

        The renderer can build only these controlled dessert types:
        - round_cake
        - square_cake
        - tiered_cake
        - cupcake
        - donut
        - cheesecake
        - brownie
        - cookie
        - tart
        - glass_dessert
        - macaron
        - mousse

        Allowed decorations:
        - strawberry
        - cherry
        - blueberry
        - raspberry
        - chocolate_curls
        - chocolate_chips
        - sprinkles
        - powdered_sugar
        - caramel_drip
        - chocolate_drip
        - cream_dollops
        - cookie_crumbs
        - mint_leaf
        - candle

        Return ONLY valid JSON.
        Do not use markdown.
        Do not explain anything.
        Do not include comments.

        JSON schema:
        {
          "dessert_type": "cupcake",
          "title": "short dessert title",
          "description": "short visual description in Spanish",
          "visual_style": "premium bakery",
          "quality_mode": "preview_and_final",
          "flavor_profile": {
            "main_flavor": "vanilla",
            "secondary_flavor": "strawberry",
            "sweetness": "medium"
          },
          "colors": {
            "base": "#D8A35D",
            "cream": "#F7D6E8",
            "accent": "#C92A3A",
            "plate": "#F8F5F0",
            "background": "#F2E7DA"
          },
          "structure": {
            "layers": 1,
            "height": "medium",
            "shape": "round",
            "has_filling": false,
            "has_drip": false,
            "has_frosting": true
          },
          "materials": {
            "base": "baked_cake",
            "cream": "satin_frosting",
            "topping": "glossy_fruit"
          },
          "decorations": [
            {
              "type": "cherry",
              "amount": 1,
              "placement": "top_center",
              "color": "#B51224"
            }
          ],
          "scene": {
            "plate": true,
            "camera": "three_quarter_closeup",
            "lighting": "soft_studio",
            "background": "warm_minimal",
            "export_ratio": "1:1"
          },
          "render_hints": {
            "preview_triangles": 120000,
            "final_triangles": 400000,
            "use_instancing": true,
            "use_soft_shadows": true,
            "use_depth_of_field": true
          }
        }

        User dessert idea:
        "%s"

        Requested visual style:
        "%s"

        Requested quality mode:
        "%s"
        """.formatted(userPrompt, style, quality);
    }

    private String extractJson(String text) {
        if (text == null || text.isBlank()) {
            return "{}";
        }

        String cleaned = text.trim()
                .replace("```json", "")
                .replace("```", "")
                .trim();

        if (cleaned.startsWith("{") && cleaned.endsWith("}")) {
            return cleaned;
        }

        Matcher matcher = Pattern.compile("\\{[\\s\\S]*}").matcher(cleaned);
        if (matcher.find()) {
            return matcher.group();
        }

        return "{}";
    }

    private JsonNode fallbackRecipe(String userPrompt, String visualStyle, String qualityMode) {
        try {
            String prompt = userPrompt == null ? "" : userPrompt.toLowerCase();

            String dessertType = "round_cake";
            if (prompt.contains("cupcake")) dessertType = "cupcake";
            else if (prompt.contains("dona") || prompt.contains("donut")) dessertType = "donut";
            else if (prompt.contains("cheesecake")) dessertType = "cheesecake";
            else if (prompt.contains("brownie")) dessertType = "brownie";
            else if (prompt.contains("galleta") || prompt.contains("cookie")) dessertType = "cookie";
            else if (prompt.contains("tarta") || prompt.contains("tartaleta")) dessertType = "tart";
            else if (prompt.contains("vaso") || prompt.contains("copa")) dessertType = "glass_dessert";
            else if (prompt.contains("macaron")) dessertType = "macaron";

            String base = "#D8A35D";
            String cream = "#FFF2D8";
            String accent = "#C92A3A";

            if (prompt.contains("chocolate")) {
                base = "#4B2418";
                cream = "#6B3525";
                accent = "#D9A441";
            } else if (prompt.contains("fresa") || prompt.contains("rosa")) {
                base = "#F2C6D4";
                cream = "#FFD6E7";
                accent = "#C92A3A";
            } else if (prompt.contains("limon") || prompt.contains("pistacho")) {
                base = "#DCE8A2";
                cream = "#EEF5C4";
                accent = "#7B9B3A";
            }

            String json = """
            {
              "dessert_type": "%s",
              "title": "Postre generado",
              "description": "Postre procedural generado a partir de la idea del usuario.",
              "visual_style": "%s",
              "quality_mode": "%s",
              "flavor_profile": {
                "main_flavor": "vanilla",
                "secondary_flavor": "cream",
                "sweetness": "medium"
              },
              "colors": {
                "base": "%s",
                "cream": "%s",
                "accent": "%s",
                "plate": "#F8F5F0",
                "background": "#F2E7DA"
              },
              "structure": {
                "layers": 2,
                "height": "medium",
                "shape": "round",
                "has_filling": true,
                "has_drip": true,
                "has_frosting": true
              },
              "materials": {
                "base": "baked_cake",
                "cream": "satin_frosting",
                "topping": "glossy_fruit"
              },
              "decorations": [
                {
                  "type": "strawberry",
                  "amount": 5,
                  "placement": "top_ring",
                  "color": "#C92A3A"
                },
                {
                  "type": "chocolate_curls",
                  "amount": 12,
                  "placement": "top_random",
                  "color": "#3A1D13"
                }
              ],
              "scene": {
                "plate": true,
                "camera": "three_quarter_closeup",
                "lighting": "soft_studio",
                "background": "warm_minimal",
                "export_ratio": "1:1"
              },
              "render_hints": {
                "preview_triangles": 120000,
                "final_triangles": 400000,
                "use_instancing": true,
                "use_soft_shadows": true,
                "use_depth_of_field": true
              }
            }
            """.formatted(
                    dessertType,
                    cleanOrDefault(visualStyle, "premium bakery"),
                    cleanOrDefault(qualityMode, "preview_and_final"),
                    base,
                    cream,
                    accent
            );

            return objectMapper.readTree(json);
        } catch (Exception ex) {
            return objectMapper.createObjectNode()
                    .put("dessert_type", "round_cake")
                    .put("title", "Postre generado")
                    .put("description", "Receta fallback");
        }
    }

    private String cleanOrDefault(String value, String fallback) {
        if (value == null || value.isBlank()) {
            return fallback;
        }
        return value.trim();
    }
}