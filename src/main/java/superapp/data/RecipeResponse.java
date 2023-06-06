package superapp.data;

import java.util.List;
import java.util.Iterator;

public class RecipeResponse {
	private String id;
	private String recipeName;
	private String title;
	private String image;
	private NutrientEntity calories;
	private NutrientEntity fat;
	private NutrientEntity carbs;
	private NutrientEntity protein;

	private List<IngredientEntity> extendedIngredients;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public NutrientEntity getCalories() {
		return calories;
	}

	public void setCalories(NutrientEntity calories) {
		this.calories = calories;
	}

	public NutrientEntity getFat() {
		return fat;
	}

	public void setFat(NutrientEntity fat) {
		this.fat = fat;
	}

	public NutrientEntity getCarbs() {
		return carbs;
	}

	public void setCarbs(NutrientEntity carbs) {
		this.carbs = carbs;
	}

	public NutrientEntity getProtein() {
		return protein;
	}

	public void setProtein(NutrientEntity protein) {
		this.protein = protein;
	}

	@Override
	public String toString() {
		return "Recipe{" +
				"id=" + id +
				", title='" + title + '\'' +
				", image='" + image + '\'' +
				", calories=" + calories +
				", fat='" + fat + '\'' +
				", carbs='" + carbs + '\'' +
				", protein='" + protein + '\'' +
				'}';
	}

	public String getRecipeName() {
		return recipeName;
	}

	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}

	public List<IngredientEntity> getExtendedIngredients() {
		return extendedIngredients;
	}

	public void setExtendedIngredients(List<IngredientEntity> extendedIngredients) {
		this.extendedIngredients = extendedIngredients;
	}

	public RecipeResponse addIngredientAndCaculate(IngredientEntity ingredient) {
		if(!extendedIngredients.contains(ingredient)){
			extendedIngredients.add(ingredient);
		}else {
			int index = extendedIngredients.indexOf(ingredient);
			extendedIngredients.set(index, ingredient);
		}
		calculate();
		return this;
	}

	private void calculate() {
		double totalFat =0.0;
		double totalCalories = 0.0;
		double totalCarbs = 0.0;
		double totalProtein = 0.0;
		String fatUnit ="";
		String caloriesUnit ="";
		String carbsUnit = "";
		String proteinUnit = "";
		for (int i = 0; i < this.extendedIngredients.size(); i++) {
			for(int j = 0; j < extendedIngredients.get(i).getNutrition().getNutrients().size(); j++) {
				String nutrientName = extendedIngredients.get(i).getNutrition().getNutrients().get(j).getName();
				double nutrientAmount = extendedIngredients.get(i).getNutrition().getNutrients().get(j).getAmount();
				String nutrientUnit =  extendedIngredients.get(i).getNutrition().getNutrients().get(j).getUnit();

				switch (nutrientName) {
				case "Calories":
					totalCalories += nutrientAmount;
					caloriesUnit = nutrientUnit;
					break;
				case "Fat":
					totalFat += nutrientAmount;
					fatUnit = nutrientUnit;
					break;
				case "Net Carbohydrates":
					totalCarbs += nutrientAmount;
					carbsUnit = nutrientUnit;
					break;
				case "Protein":
					totalProtein += nutrientAmount;
					proteinUnit = nutrientUnit;
					break;
				default:
					break;
				}
			}
		}
		this.setCalories(new NutrientEntity("calories", totalCalories, caloriesUnit));
		this.setFat(new NutrientEntity("fat", totalFat, fatUnit));
		this.setCarbs(new NutrientEntity("Net Carbohydrates", totalCarbs, carbsUnit));
		this.setProtein(new NutrientEntity("protein", totalProtein, proteinUnit));
	}

	public RecipeResponse RemoveIngredientAndCaculate(int ingredientId) {
		Iterator<IngredientEntity> iterator = extendedIngredients.iterator();
		while (iterator.hasNext()) {
			IngredientEntity ingredient = iterator.next();
			// Check if the ingredient ID matches the desired ID
			if (ingredient.getId() == ingredientId) {
				// Remove the ingredient from the list
				iterator.remove();
				// Exit the loop since the ingredient is found and removed
				break;
			}
		}
		calculate();
		return this;		

	}
}
