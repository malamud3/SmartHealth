package superapp.data.subEntity;

import java.util.ArrayList;

public class NutritionEntity {
	 ArrayList < Object > nutrients = new ArrayList < Object > ();
	 ArrayList < Object > properties = new ArrayList < Object > ();
	 ArrayList < Object > flavonoids = new ArrayList < Object > ();
	 CaloricBreakdownEntity CaloricBreakdownObject;
	 WeightPerServingEntity WeightPerServingObject;


	 // Getter Methods 

	 public CaloricBreakdownEntity getCaloricBreakdown() {
	  return CaloricBreakdownObject;
	 }

	 public WeightPerServingEntity getWeightPerServing() {
	  return WeightPerServingObject;
	 }

	 // Setter Methods 

	 public void setCaloricBreakdown(CaloricBreakdownEntity caloricBreakdownObject) {
	  this.CaloricBreakdownObject = caloricBreakdownObject;
	 }

	 public void setWeightPerServing(WeightPerServingEntity weightPerServingObject) {
	  this.WeightPerServingObject = weightPerServingObject;
	 }
	}