package superapp.logic.service;

import superapp.data.subEntity.IngridientEntity;

public interface SpoonaculerService {

	public IngridientEntity getIngredientDataByName(String ingredientName);
	
	public IngridientEntity getIngredientDataById(Integer id);
	
}
