package superapp.logic.utilitys;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.activemq.artemis.commons.shaded.json.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;

import superapp.data.IngredientEntity;
import superapp.data.NutrientEntity;
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
		API_KEY = "837f9584e1af42d2a8255fca1f40e6c9";

		API_BASE_URL = "https://api.spoonacular.com";
		//this.jackson = new ObjectMapper();
		this.restTemplate = new RestTemplate();
	}

	@Override
	public IngredientEntity getIngredientDataByName(String ingredientName) {
		String resultJSON = this.restTemplate
				.getForObject(API_BASE_URL + "/food/ingredients/search?query={query}&apiKey={apiKey}"
						, String.class, ingredientName, API_KEY);

		Integer id = getIdOfTheFirstResult(resultJSON);
		System.err.println(resultJSON);
		//100 grams by default
		return getIngredientDataByIdAndAmountInGrams(id, 100);
	}

	@Override
	public IngredientEntity getIngredientDataByNameAndAmount(String ingredientName, double amountInGrams) {
		String resultJSON = this.restTemplate
				.getForObject(API_BASE_URL + "/food/ingredients/search?query={query}&apiKey={apiKey}"
						, String.class, ingredientName, API_KEY);

		Integer id = getIdOfTheFirstResult(resultJSON);
		System.err.println(resultJSON);
		return getIngredientDataByIdAndAmountInGrams(id, amountInGrams);
	}


	@Override
	public IngredientEntity getIngredientDataByIdAndAmountInGrams(Integer id, double amountInGrams) {

		return this.restTemplate
				.getForObject(API_BASE_URL + "/food/ingredients/{id}/information?apiKey={apiKey}&amount={amount}&unit=grams",
						IngredientEntity.class,id, API_KEY, amountInGrams);
	}

	@Override
	public RecipeResponse getRecipeByName(String recipeName) {

		RecipeResponse response = new RecipeResponse();
		response.setRecipeName(recipeName);
		String recipeObjectString =  this.restTemplate
				.getForObject(API_BASE_URL + "/recipes/complexSearch?apiKey={apiKey}&query={query}",
						String.class,API_KEY, recipeName);
		//System.err.println(recipeObjectString);
		JSONObject recipeObject = new JSONObject(recipeObjectString); 
		JSONObject firstResult = recipeObject.getJSONArray("results").getJSONObject(0);
		response.setId(String.valueOf(firstResult.getInt("id")));
		response.setImage(firstResult.getString("image"));
		response.setTitle(firstResult.getString("title"));
		return calculateRecipseDetails(response);
	}

	@Override
	public RecipeResponse getRecipeByNameAndDiet(String recipeName, String diet) {
		RecipeResponse response = new RecipeResponse();
		response.setRecipeName(recipeName);
		String recipeObjectString =  this.restTemplate
				.getForObject(API_BASE_URL + "/recipes/complexSearch?apiKey={apiKey}&query={query}&diet={diet}",
						String.class,API_KEY, recipeName, diet);
		System.err.println(recipeObjectString);
		JSONObject recipeObject = new JSONObject(recipeObjectString); 
		JSONObject firstResult = recipeObject.getJSONArray("results").getJSONObject(0);
		response.setId(String.valueOf(firstResult.getInt("id")));
		response.setImage(firstResult.getString("image"));
		response.setTitle(firstResult.getString("title"));
		return calculateRecipseDetails(response);
	}

	@Override
	public List<RecipeResponse> getRandomRecipe(int number) {
		String recipeListJSON = this.restTemplate.getForObject(API_BASE_URL + "/recipes/random?apiKey={apiKey}&number={number}",
				String.class, API_KEY, number );
		
		JSONObject jsonObject = new JSONObject(recipeListJSON);
        JSONArray recipesArray = jsonObject.getJSONArray("recipes");
		List<RecipeResponse> recipeResponses = new ArrayList<>();
		
        for (int i = 0; i < recipesArray.length(); i++) {
        	RecipeResponse response = new RecipeResponse();
            JSONObject recipeObject = recipesArray.getJSONObject(i);
            
            response.setId(String.valueOf(recipeObject.getInt("id")));
            response.setImage(recipeObject.getString("image"));
            response.setTitle(recipeObject.getString("title"));
            
            recipeResponses.add(calculateRecipseDetails(response));
        }
        return recipeResponses;
	}

	private RecipeResponse calculateRecipseDetails(RecipeResponse recipeObject) {
		String nutrientsString = this.restTemplate.
				getForObject(API_BASE_URL + "/recipes/{id}/nutritionWidget.json/?apiKey={apiKey}", String.class, recipeObject.getId(),
						API_KEY);

		JSONArray nutrients = new JSONObject(nutrientsString).getJSONArray("nutrients");

		for (int i = 0; i < nutrients.length(); i++) {
			String nutrientName = nutrients.getJSONObject(i).getString("name");
			double nutrientAmount = nutrients.getJSONObject(i).getDouble("amount");
			String nutrientUnit =  nutrients.getJSONObject(i).getString("unit");

			switch (nutrientName) {
			case "Calories":
				recipeObject.setCalories(new NutrientEntity(nutrientName,nutrientAmount, nutrientUnit));
				break;
			case "Fat":
				recipeObject.setFat(new NutrientEntity(nutrientName,nutrientAmount, nutrientUnit));
				break;
			case "Net Carbohydrates":
				recipeObject.setCarbs(new NutrientEntity(nutrientName,nutrientAmount, nutrientUnit));
				break;
			case "Protein":
				recipeObject.setProtein(new NutrientEntity(nutrientName,nutrientAmount, nutrientUnit));
				break;
			default:
				break;
			}
		}
		return fetchIngredients(recipeObject);
	}


	private RecipeResponse fetchIngredients(RecipeResponse recipeObject) {
		RecipeResponse temp = this.restTemplate.getForObject(API_BASE_URL + "/recipes/{id}/information?apiKey={apiKey}",
				RecipeResponse.class, recipeObject.getId(), API_KEY);
		recipeObject.setExtendedIngredients(temp.getExtendedIngredients());
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
