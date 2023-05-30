package superapp.data;

import java.util.ArrayList;

public class NutritionEntity {
	ArrayList <NutrientEntity> nutrients = new ArrayList < NutrientEntity > ();
	ArrayList <PropertyEntity> properties = new ArrayList < PropertyEntity > ();
	//ArrayList < Object > flavonoids = new ArrayList < Object > ();
	//WeightPerServingEntity WeightPerServingObject;
	CaloricBreakdownEntity caloricBreakdown = new CaloricBreakdownEntity();


	public NutritionEntity() {
		super();
	}


	public ArrayList<NutrientEntity> getNutrients() {
		return nutrients;
	}


	public void setNutrients(ArrayList<NutrientEntity> nutrients) {
		this.nutrients = nutrients;
	}


	public ArrayList<PropertyEntity> getProperties() {
		return properties;
	}


	public void setProperties(ArrayList<PropertyEntity> properties) {
		this.properties = properties;
	}


	public CaloricBreakdownEntity getCaloricBreakdown() {
		return caloricBreakdown;
	}


	public void setCaloricBreakdown(CaloricBreakdownEntity caloricBreakdown) {
		this.caloricBreakdown = caloricBreakdown;
	}
	
	
}