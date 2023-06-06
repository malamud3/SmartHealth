package superapp.data;

public class IngredientEntity {
	private int id;
	private String name;
	private float amount;
	private String unit;
	private String image;
	private NutritionEntity nutrition;
	
	public IngredientEntity() {}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public void setImage(String image){
		this.image = image;
	}
	
	public String getImage(){
		return this.image;
	}
	
	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public NutritionEntity getNutrition() {
		return nutrition;
	}

	public void setNutrition(NutritionEntity nutrition) {
		this.nutrition = nutrition;
	}

}








