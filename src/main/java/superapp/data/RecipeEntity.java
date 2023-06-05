package superapp.data;

import java.util.ArrayList;

public class RecipeEntity {
    private int id;
    private String title;
    private String summary;
    private String imageURL;
    private int servings;
    private int readyIn;
    private String fat;
    private String carbs;
    private String protein;
    private int calories;

    private ArrayList< IngredientEntity > ingredients;


    public RecipeEntity() {}

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setReadyIn(int readyIn) {
        this.readyIn = readyIn;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getCarbs() {
        return carbs;
    }

    public void setCarbs(String carbs) {
        this.carbs = carbs;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public int getServings() {
        return servings;
    }

    public int getReadyIn() {
        return readyIn;
    }

    public ArrayList<IngredientEntity> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<IngredientEntity> ingredients) {
        this.ingredients = ingredients;
    }
}
