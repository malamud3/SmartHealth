package superapp.data.subEntity;

import java.util.ArrayList;

public class IngridientEntity {



	//TODO - GET STRUCT FROM API

	private float id;
	private String original;
	private String originalName;
	private String name;
	private float amount;
	private String unit;
	private String unitShort;
	private String unitLong;
	ArrayList < Object > possibleUnits = new ArrayList < Object > ();
	EstimatedCostEntity EstimatedCostObject;
	private String consistency;
	ArrayList < Object > shoppingListUnits = new ArrayList < Object > ();
	private String aisle;
	private String image;
	ArrayList < Object > meta = new ArrayList < Object > ();
	NutritionEntity NutritionObject;
	ArrayList < Object > categoryPath = new ArrayList < Object > ();


	// Getter Methods 

	public float getId() {
		return id;
	}

	public String getOriginal() {
		return original;
	}

	public String getOriginalName() {
		return originalName;
	}

	public String getName() {
		return name;
	}

	public float getAmount() {
		return amount;
	}

	public String getUnit() {
		return unit;
	}

	public String getUnitShort() {
		return unitShort;
	}

	public String getUnitLong() {
		return unitLong;
	}

	public EstimatedCostEntity getEstimatedCost() {
		return EstimatedCostObject;
	}

	public String getConsistency() {
		return consistency;
	}

	public String getAisle() {
		return aisle;
	}

	public String getImage() {
		return image;
	}

	public NutritionEntity getNutrition() {
		return NutritionObject;
	}

	// Setter Methods 

	public void setId(float id) {
		this.id = id;
	}

	public void setOriginal(String original) {
		this.original = original;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setUnitShort(String unitShort) {
		this.unitShort = unitShort;
	}

	public void setUnitLong(String unitLong) {
		this.unitLong = unitLong;
	}

	public void setEstimatedCost(EstimatedCostEntity estimatedCostObject) {
		this.EstimatedCostObject = estimatedCostObject;
	}

	public void setConsistency(String consistency) {
		this.consistency = consistency;
	}

	public void setAisle(String aisle) {
		this.aisle = aisle;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setNutrition(NutritionEntity nutritionObject) {
		this.NutritionObject = nutritionObject;
	}
}








