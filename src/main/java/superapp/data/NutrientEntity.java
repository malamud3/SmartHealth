package superapp.data;

public class NutrientEntity {
	public String name;
    public double amount;
    public String unit;
    //public double percentOfDailyNeeds;
	
    
    public NutrientEntity() {
		super();
	}
    
    public NutrientEntity(String name, double amount, String unit) {
		this.name = name;
		this.amount = amount;
		this.unit = unit;
	}
    
    
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
//	public double getPercentOfDailyNeeds() {
//		return percentOfDailyNeeds;
//	}
//	public void setPercentOfDailyNeeds(double percentOfDailyNeeds) {
//		this.percentOfDailyNeeds = percentOfDailyNeeds;
//	}
    
    
}
