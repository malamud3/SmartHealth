package superapp.data.subEntity;

import java.util.ArrayList;

import superapp.data.subEntity.optionalSubEntity.EstimatedCostEntity;

public class IngridientEntity {



	//TODO - GET STRUCT FROM API

	private int id;
	private String original;
	private String originalName;
	//private String name;
	private float amount;
	private String unit;
	//private String unitShort;
	//private String unitLong;
	//ArrayList < Object > possibleUnits = new ArrayList < Object > ();
	//EstimatedCostEntity EstimatedCostObject;
	//private String consistency;
	//ArrayList < Object > shoppingListUnits = new ArrayList < Object > ();
	//private String aisle;
	//private String image;
	//ArrayList < Object > meta = new ArrayList < Object > ();
	Nutrition nutrition;
	ArrayList < Object > categoryPath = new ArrayList < Object > ();

	
	
	public IngridientEntity() {
		super();
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getOriginal() {
		return original;
	}



	public void setOriginal(String original) {
		this.original = original;
	}



	public String getOriginalName() {
		return originalName;
	}



	public void setOriginalName(String originalName) {
		this.originalName = originalName;
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



	public Nutrition getNutrition() {
		return nutrition;
	}



	public void setNutrition(Nutrition nutrition) {
		this.nutrition = nutrition;
	}



	public ArrayList<Object> getCategoryPath() {
		return categoryPath;
	}



	public void setCategoryPath(ArrayList<Object> categoryPath) {
		this.categoryPath = categoryPath;
	}

	

}








