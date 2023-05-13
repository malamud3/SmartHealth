package superapp.data.subEntity;

public class CaloricBreakdownEntity {
	private float percentProtein;
	private float percentFat;
	private float percentCarbs;


	
	public CaloricBreakdownEntity() {
		super();
	}

	// Getter Methods 

	public float getPercentProtein() {
		return percentProtein;
	}

	public float getPercentFat() {
		return percentFat;
	}

	public float getPercentCarbs() {
		return percentCarbs;
	}

	// Setter Methods 

	public void setPercentProtein(float percentProtein) {
		this.percentProtein = percentProtein;
	}

	public void setPercentFat(float percentFat) {
		this.percentFat = percentFat;
	}

	public void setPercentCarbs(float percentCarbs) {
		this.percentCarbs = percentCarbs;
	}
}
