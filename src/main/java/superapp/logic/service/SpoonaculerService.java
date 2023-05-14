package superapp.logic.service;

import superapp.data.subEntity.IngredientEntity;

public interface SpoonaculerService {

	public IngredientEntity getIngredientDataByName(String ingredientName);
	
	public IngredientEntity getIngredientDataById(Integer id);
	
}
