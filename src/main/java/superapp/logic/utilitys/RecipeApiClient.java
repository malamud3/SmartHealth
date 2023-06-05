package superapp.logic.utilitys;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import superapp.data.RecipeResponse;

public class RecipeApiClient {

    private static final String API_KEY = "84eb8e78605740d1b536934fa885e448";
    private static final String FETCH_RECIPES_URL = "https://api.spoonacular.com/recipes/random";

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static void main(String[] args) {
        List<RecipeResponse> recipes = fetchRecipesWithParams(1);
        System.out.println(recipes);
    }

    public static List<RecipeResponse> fetchRecipesWithParams(int recipes_number) {
        List<RecipeResponse> _recipes = new ArrayList<>();

        try {
            HttpClient httpClient = HttpClient.newHttpClient();

            URI uri = new URI(FETCH_RECIPES_URL + "?apiKey=" + API_KEY + "&number=" + recipes_number);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new IOException("Unexpected response code: " + response.statusCode());
            }

            String responseBody = response.body();
            Map<String, Object> responseJson = objectMapper.readValue(responseBody, Map.class);
            List<Map<String, Object>> recipeJsonList = (List<Map<String, Object>>) responseJson.get("recipes");

            for (Map<String, Object> recipeJson : recipeJsonList) {
                int id = (int) recipeJson.get("id");
                String title = (String) recipeJson.get("title");
                String image = (String) recipeJson.get("image");

                String nutritionUrl = "https://api.spoonacular.com/recipes/" + id + "/nutritionWidget.json/";
                URI nutritionUri = new URI(nutritionUrl + "?apiKey=" + API_KEY);
                HttpRequest nutritionRequest = HttpRequest.newBuilder()
                        .uri(nutritionUri)
                        .build();

                HttpResponse<String> nutritionResponse = httpClient.send(nutritionRequest, HttpResponse.BodyHandlers.ofString());
                if (nutritionResponse.statusCode() != 200) {
                    throw new IOException("Unexpected response code: " + nutritionResponse.statusCode());
                }

                String nutritionResponseBody = nutritionResponse.body();
                Map<String, Object> nutritionJson = objectMapper.readValue(nutritionResponseBody, Map.class);
                List<Map<String, Object>> nutrientsJsonList = (List<Map<String, Object>>) nutritionJson.get("nutrients");

                RecipeResponse recipe = new RecipeResponse();
                recipe.setId(id);
                recipe.setTitle(title);
                recipe.setImage(image);
                recipe.setCalories(0);
                recipe.setFat("");
                recipe.setProtein("");
                recipe.setCarbs("");

                for (Map<String, Object> nutrientJson : nutrientsJsonList) {
                    String nutrientName = (String) nutrientJson.get("name");
                    double nutrientAmount = (double) nutrientJson.get("amount");
                    String nutrientUnit = (String) nutrientJson.get("unit");

                    switch (nutrientName) {
                        case "Calories":
                            recipe.setCalories(nutrientAmount);
                            break;
                        case "Fat":
                            recipe.setFat(nutrientAmount + nutrientUnit);
                            break;
                        case "Net Carbohydrates":
                            recipe.setCarbs(nutrientAmount + nutrientUnit);
                            break;
                        case "Protein":
                            recipe.setProtein(nutrientAmount + nutrientUnit);
                            break;
                        default:
                            break;
                    }
                }

                _recipes.add(recipe);
            }
        } catch (IOException | InterruptedException | URISyntaxException e) {
            e.printStackTrace();
        }

        return _recipes;
    }


}
