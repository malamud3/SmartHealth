package superapp.data;

import java.util.List;

public class RecipeEntity {
    int id;
    String title;
    String imageURL;
    int servings;
    int readyIn;
    List< IngredientEntity > ingredients;
    public List<String> cuisines;
    public List<String> dishTypes;
    public List<String> diets;

    public RecipeEntity(  ) {

    }
    public RecipeEntity( int id, String title, String imageURL ) {
        this.id = id;
        this.title = title;
        this.imageURL = imageURL;
    }

    public RecipeEntity( int id, String title, String imageURL, int servings, int readyIn, List< IngredientEntity > ingredients ) {
        this.id = id;
        this.title = title;
        this.imageURL = imageURL;
        this.servings = servings;
        this.readyIn = readyIn;
        this.ingredients = ingredients;
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


    public List<IngredientEntity> getIngredients() {
        return ingredients;
    }
}
