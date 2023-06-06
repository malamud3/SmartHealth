package superapp.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import superapp.Boundary.*;
import superapp.Boundary.User.UserId;

import java.util.*;
import java.util.stream.Collectors;

@Document(collection = "SUPER_APP_OBJECTS")
public class SuperAppObjectEntity {

	@Id
	private ObjectId objectId;
	private String type; //todo- define types
	private String alias;
	private Boolean active;
	private Date creationTimestamp;


	//private Location location;

	@GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
	private Point location;//using Point for distance calculations

	private CreatedBy createdBy;
	private Map<String, Object> objectDetails;

	@DBRef
	@Field("parentObjects")
	private Set<SuperAppObjectEntity> parentObjects;
	@DBRef
	@Field("childObjects")
	private Set<SuperAppObjectEntity> childObjects;

	public SuperAppObjectEntity(String superapp,String internalObjectId) {
		objectId = new ObjectId(superapp, internalObjectId);
	}

	public SuperAppObjectEntity() {

	}

	public ObjectId getObjectId() {
		return objectId;
	}
	public void setObjectId(ObjectId objectId) {
		this.objectId = objectId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public Date getCreationTimestamp() {
		return creationTimestamp;
	}
	public void setCreationTimestamp(Date creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}
	public Point getLocation() {
		return location;
	}
	public void setLocation(Point location) {
		this.location = location;
	}
	public CreatedBy getCreatedBy() {
		return this.createdBy;
	}
	public void setCreatedBy(CreatedBy createdBy) {
		this.createdBy = createdBy;
	}


	public Map<String, Object> getObjectDetails() {
		return objectDetails;
	}

	public void setObjectDetails(Map<String, Object> objectDetails) {
		this.objectDetails = objectDetails;
	}

	public Set<SuperAppObjectEntity> getParentObjects() {
		if (parentObjects == null) {
			parentObjects = new HashSet<SuperAppObjectEntity>();
		}
		return parentObjects;
	}

	public void setParentObjects(Set<SuperAppObjectEntity> parentObjects) {
		this.parentObjects = parentObjects;
	}

	public Set<SuperAppObjectEntity> getChildObjects() {
		if (childObjects == null) {
			childObjects = new HashSet<SuperAppObjectEntity>();
		}
		return childObjects;
	}

	public void setChildObjects(Set<SuperAppObjectEntity> childObjects) {
		this.childObjects = childObjects;
	}

	public void addParentObject(SuperAppObjectEntity parentObject) {
		if (parentObjects == null) {
			parentObjects = new HashSet<>();
		}
		parentObjects.add(parentObject);
		parentObject.addChildObject(this);
	}

	public void addChildObject(SuperAppObjectEntity childObject) {
		if (childObjects == null) {
			childObjects = new HashSet<>();
		}
		childObjects.add(childObject);
		childObject.addParentObject(this);
	}


	@Override
	public int hashCode() {
		return Objects.hash(objectId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SuperAppObjectEntity other = (SuperAppObjectEntity) obj;
		return objectId.equals(other.getObjectId());
	}

	@SuppressWarnings("unchecked")
	public RecipeResponse insertNewRecipeToObjectDetails(RecipeResponse recipe) {
		List<RecipeResponse> recipes =  (List<RecipeResponse>) objectDetails.get("recipes");
		if (recipes == null) {
			recipes = new ArrayList<>();
		}
		recipes.add(recipe);
		objectDetails.put("recipes", recipes);
		return recipe;
	}

	@SuppressWarnings("unchecked")
	public void deleteRecipeFromObjectDetails(String recipeId) {
		List<RecipeResponse> recipes =  (List<RecipeResponse>) objectDetails.get("recipes");
		if (recipes == null) {
			recipes = new ArrayList<>();
		}
		recipes = recipes.stream()
				.filter(recipe -> !recipe.getId().equals(recipeId))
				.toList();
		objectDetails.put("recipes", recipes);
	}

	public void removeParentObject(SuperAppObjectEntity parentObject) {
		if (parentObjects != null) {
			parentObjects.remove(parentObject);
			parentObject.removeChildObject(this);
		}
	}

	public void removeChildObject(SuperAppObjectEntity childObject) {
		if (childObjects != null) {
			childObjects.remove(childObject);
			childObject.removeParentObject(this);
		}
	}

	public void deleteAllRecipes() {
		List<RecipeResponse> recipes =  new ArrayList<>();
		objectDetails.put("recipes", recipes);		
	}

	@SuppressWarnings("unchecked")

	public void addFollowerToObjectDetails(UserId userId) {
		List<UserId> followers =  (List<UserId>) objectDetails.get("followers");
		if (followers == null) {
			followers = new ArrayList<>();
		}
		if (!followers.contains(userId)) {
			followers.add(userId);
		}
		objectDetails.put("followers", followers);
	}		

	@SuppressWarnings("unchecked")
	public void deleteFollowerFromObjectDetails(UserId userId) {
		List<UserId> followers =  (List<UserId>) objectDetails.get("followers");
		if (followers == null) {
			followers = new ArrayList<>();
		}
		followers = followers.stream()
				.filter(follower -> !follower.equals(userId))
				.toList();
		objectDetails.put("followers", followers);
	}

	@SuppressWarnings("unchecked")
	public RecipeResponse createRecipe(String recipeName) {
		
		RecipeResponse newRecipeResponse = initRecipe(recipeName);
		return insertNewRecipeToObjectDetails(newRecipeResponse);
	}

	private RecipeResponse initRecipe(String recipeName) {
		RecipeResponse newRecipeResponse = new RecipeResponse();
		newRecipeResponse.setRecipeName(recipeName);
		newRecipeResponse.setId(UUID.randomUUID().toString());
		newRecipeResponse.setCalories(new NutrientEntity());
		newRecipeResponse.setCarbs(new NutrientEntity());
		newRecipeResponse.setExtendedIngredients(new ArrayList<IngredientEntity>());
		newRecipeResponse.setFat(new NutrientEntity());
		newRecipeResponse.setImage("");
		newRecipeResponse.setProtein(new NutrientEntity());
		newRecipeResponse.setTitle(recipeName);
		return newRecipeResponse;
		
	}
	
	@SuppressWarnings("unchecked")
	public RecipeResponse addIngredientToRecipe(String recipeId, IngredientEntity ingredient) {
		List<RecipeResponse> recipes =  (List<RecipeResponse>) objectDetails.get("recipes");
		if (recipes == null) {
			throw new RuntimeException("cannot add ingredient, there are no recipes!");
		}
		RecipeResponse theRecipe = recipes.stream()
		        .filter(recipe -> recipe.getId().equals(recipeId))
		        .findFirst()
		        .orElse(null);
		if (theRecipe == null) {
			throw new RuntimeException("there is no recipe with this Id");
		}
		theRecipe.addIngredientAndCaculate(ingredient);
		return theRecipe;
		
	}

	@SuppressWarnings("unchecked")
	public RecipeResponse removeIngredientFromRecipe(String recipeId, int ingredientId) {
		List<RecipeResponse> recipes =  (List<RecipeResponse>) objectDetails.get("recipes");
		if (recipes == null) {
			throw new RuntimeException("cannot add ingredient, there are no recipes!");
		}
		RecipeResponse theRecipe = recipes.stream()
		        .filter(recipe -> recipe.getId().equals(recipeId))
		        .findFirst()
		        .orElse(null);
		if (theRecipe == null) {
			throw new RuntimeException("there is no recipe with this Id");
		}
		theRecipe.RemoveIngredientAndCaculate(ingredientId);
		return theRecipe;
	}
	
	

}
