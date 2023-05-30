package superapp.logic.utilitys;
import org.springframework.data.geo.Metrics;

public class GeneralUtility {
	public boolean isStringEmptyOrNull(String s){
		return s == null || s.trim().isEmpty();
	}




	public Metrics parseDistanceUnit(String distanceUnitString) {
		if (distanceUnitString.equalsIgnoreCase("kilometers") || distanceUnitString.equalsIgnoreCase("km")) {
			return Metrics.KILOMETERS;
		} else if (distanceUnitString.equalsIgnoreCase("miles") || distanceUnitString.equalsIgnoreCase("mi")) {
			return Metrics.MILES;
		} else if (distanceUnitString.equalsIgnoreCase("NEUTRAL")) {
			return Metrics.NEUTRAL;
		}else {
			throw new IllegalArgumentException("Invalid distance unit: " + distanceUnitString);
		}
	}
}
