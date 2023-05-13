package superapp.data.subEntity;

import java.util.ArrayList;

public class Nutrition {
	ArrayList < Object > nutrients = new ArrayList < Object > ();
	ArrayList < Object > properties = new ArrayList < Object > ();
	//ArrayList < Object > flavonoids = new ArrayList < Object > ();
	//WeightPerServingEntity WeightPerServingObject;
	CaloricBreakdownEntity caloricBreakdown = new CaloricBreakdownEntity();


	public Nutrition() {
		super();
	}


	public ArrayList<Object> getNutrients() {
		return nutrients;
	}


	public void setNutrients(ArrayList<Object> nutrients) {
		this.nutrients = nutrients;
	}


	public ArrayList<Object> getProperties() {
		return properties;
	}


	public void setProperties(ArrayList<Object> properties) {
		this.properties = properties;
	}


	public CaloricBreakdownEntity getCaloricBreakdown() {
		return caloricBreakdown;
	}


	public void setCaloricBreakdown(CaloricBreakdownEntity caloricBreakdown) {
		this.caloricBreakdown = caloricBreakdown;
	}
	
	
}