package superapp.data.subEntity.optionalSubEntity;

import java.util.Objects;

public class EstimatedCostEntity {

	private float value;
	private String unit;

	// Getter Methods 

	public float getValue() {
		return value;
	}

	public String getUnit() {
		return unit;
	}

	// Setter Methods 

	public void setValue(float value) {
		this.value = value;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof EstimatedCostEntity that)) return false;
		return Float.compare(that.value, value) == 0 && Objects.equals(unit, that.unit);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value, unit);
	}
}
