package superapp.logic.utilitys;


import java.util.Map;

import org.apache.activemq.artemis.commons.shaded.json.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;




import superapp.data.IngredientEntity;
import superapp.data.RecipeResponse;
import superapp.logic.service.SpoonacularService;

@Service
public class SpoonacularUtility implements SpoonacularService{

	//private ObjectMapper jackson; //add if needed
	private RestTemplate restTemplate;


	private String API_KEY;
	private String API_BASE_URL;

	public SpoonacularUtility() {
		//TODO - find a way to inject the API key
		API_KEY = "4484a885c6ed4e6281e94718c241a20e";

		API_BASE_URL = "https://api.spoonacular.com";
		//this.jackson = new ObjectMapper();
		this.restTemplate = new RestTemplate();
	}

	@Override
	public IngredientEntity getIngredientDataByName(String ingredientName) {
		String resultJSON = this.restTemplate
				.getForObject(API_BASE_URL + "/food/ingredients/search" +
						"?query=" + ingredientName + "&apiKey=" + API_KEY, String.class);

		Integer id = getIdOfTheFirstResult(resultJSON);
		System.err.println(resultJSON);
		//100 grams by default
		return getIngredientDataByIdAndAmountInGrams(id, 100);
	}

	@Override
	public IngredientEntity getIngredientDataByName(String ingredientName, int amountInGrams) {
		String resultJSON = this.restTemplate
				.getForObject(API_BASE_URL + "/food/ingredients/search" +
						"?query=" + ingredientName + "&apiKey=" + API_KEY, String.class);

		Integer id = getIdOfTheFirstResult(resultJSON);
		System.err.println(resultJSON);
		return getIngredientDataByIdAndAmountInGrams(id, 100);
	}


	@Override
	public IngredientEntity getIngredientDataByIdAndAmountInGrams(Integer id, int amountInGrams) {
		System.err.println(API_BASE_URL + "/food/ingredients/{id}/information" + "?apiKey=" + API_KEY 
				+ "&amount=" + amountInGrams +"&unit=grams");
		System.err.println(this.restTemplate
				.getForObject(API_BASE_URL + "/food/ingredients/{id}/information" + "?apiKey=" + API_KEY + "&amount=" + amountInGrams +"&unit=grams",
						String.class, id));
		return this.restTemplate
				.getForObject(API_BASE_URL + "/food/ingredients/{id}/information" + "?apiKey=" + API_KEY + "&amount=" + amountInGrams +"&unit=grams",
						IngredientEntity.class, id);
	}
	
	@Override
	public RecipeResponse getRecipeByName(String recipeName) {
		
		RecipeResponse response = new RecipeResponse();
		response.setRecipeName(recipeName);
			String recipeObjectString =  this.restTemplate
		.getForObject(API_BASE_URL + "/recipes/complexSearch?apiKey={apiKey}&query={query}",
				String.class,API_KEY, recipeName);
			System.err.println(recipeObjectString);
			JSONObject recipeObject = new JSONObject(recipeObjectString); 
			JSONObject firstResult = recipeObject.getJSONArray("results").getJSONObject(0);
			response.setId(firstResult.getInt("id"));
			response.setImage(firstResult.getString("image"));
			response.setTitle(firstResult.getString("title"));
			return calculateRecipseDetails(response);
	}
	
	private RecipeResponse calculateRecipseDetails(RecipeResponse recipeObject) {
		String nutrientsString = this.restTemplate.
				getForObject(API_BASE_URL + "/recipes/{id}/nutritionWidget.json/?apiKey=4484a885c6ed4e6281e94718c241a20e", String.class, recipeObject.getId());
		
		JSONArray nutrients = new JSONObject(nutrientsString).getJSONArray("nutrients");
		
		for (int i = 0; i < nutrients.length(); i++) {
            String nutrientName = nutrients.getJSONObject(i).getString("name");
            double nutrientAmount = nutrients.getJSONObject(i).getDouble("amount");
            String nutrientUnit =  nutrients.getJSONObject(i).getString("unit");

            switch (nutrientName) {
                case "Calories":
                    recipeObject.setCalories(nutrientAmount);
                    break;
                case "Fat":
                    recipeObject.setFat(nutrientAmount + nutrientUnit);
                    break;
                case "Net Carbohydrates":
                    recipeObject.setCarbs(nutrientAmount + nutrientUnit);
                    break;
                case "Protein":
                    recipeObject.setProtein(nutrientAmount + nutrientUnit);
                    break;
                default:
                    break;
            }
		}
		return recipeObject;
	}


	private int getIdOfTheFirstResult (String result) {
		//can add an index of a result

		JSONObject jsonObject = new JSONObject(result);

		JSONArray resultsArray = jsonObject.getJSONArray("results");

		if (resultsArray.length() > 0) {
			JSONObject firstResult = resultsArray.getJSONObject(0);
			int id = firstResult.getInt("id");
			return id;
		}
		return -1;//can change to throw a new resultemptyException
	}





}
