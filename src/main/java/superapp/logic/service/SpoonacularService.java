package superapp.logic.service;

import superapp.data.IngredientEntity;
import superapp.data.RecipeResponse;

public interface SpoonacularService {

	public IngredientEntity getIngredientDataByName(String ingredientName);
	
	public IngredientEntity getIngredientDataByIdAndAmountInGrams(Integer id, int amountInGrams);

	public IngredientEntity getIngredientDataByName(String ingredientName, int amountInGrams);
	
	public RecipeResponse getRecipeByName(String recipeName);
	
}
