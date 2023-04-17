package superapp.data;

public class AmountEntity {
	private String amountType;
	
	private float amount;

	public AmountEntity() {
		super();
	}
	
	public String getAmountType() {
		return amountType;
	}

	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}
	
	
}
