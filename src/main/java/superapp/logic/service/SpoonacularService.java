package superapp.logic.service;

import java.util.List;

import superapp.data.IngredientEntity;
import superapp.data.RecipeResponse;

public interface SpoonacularService {

	public IngredientEntity getIngredientDataByName(String ingredientName);
	
	public IngredientEntity getIngredientDataByIdAndAmountInGrams(Integer id, double amountInGrams);

	public IngredientEntity getIngredientDataByNameAndAmount(String ingredientName, double amountInGrams);
	
	public RecipeResponse getRecipeByName(String recipeName);

	public RecipeResponse getRecipeByNameAndDiet(String recipeName, String diet);
	
	public List<RecipeResponse> getRandomRecipe(int number);
	
}
