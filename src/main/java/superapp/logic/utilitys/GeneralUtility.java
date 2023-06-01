package superapp.logic.utilitys;
import org.springframework.data.geo.Metrics;

public class GeneralUtility {
	public boolean isStringEmptyOrNull(String s){
		return s == null || s.trim().isEmpty();
	}




	public double calculateRadiusInRadiansUsingDistanceUnit(String distanceUnitString, double distance) {
		if (distanceUnitString.equalsIgnoreCase("kilometers") || distanceUnitString.equalsIgnoreCase("km")) {
			return distance / 6378.1;
		} else if (distanceUnitString.equalsIgnoreCase("miles") || distanceUnitString.equalsIgnoreCase("mi")) {
			return distance / 3963.2;
		}else {
			throw new IllegalArgumentException("Invalid distance unit: " + distanceUnitString);
		}
	}
}
