import java.util.HashMap;

public class CompPlants {
	
	/**
	 * Accepts a plantName string, and returns a formatted string with plant info. 
	 * Called when listClickedHandler is called. 
	 * @param String plantName
	 * @return String infoString
	 */
	public static String getInfo(String s) {
		Plant p = Garden.getPlant(s);
		String temp = p.getCommonName() + "\n";
		temp = temp + p.getScientificName() + "\n";
		temp = temp + p.getFamily() + "\n";
		temp = temp + p.getColor() + "\n";
		temp = temp + p.getSizeLower() + "\n";
		temp = temp + p.getSizeUpper() + "\n";
		temp = temp + p.getSpreadRadiusLower() + "\n";
		temp = temp + p.getSpreadRadiusUpper() + "\n";
		return temp;
		//return p.toString();
	}
	
	/**
	 * This method accepts a plant name as a string, and returns the number of leps supported by that plant. 
	 * @param String plantName 
	 * @return int lepsSupported
	 */
	public static int getLepInfo(String s) {
		Plant p = Garden.getPlant(s);
		return p.getLepsSupported();
	}
	
	/**
	 * Accepts a plant name as a string, and returns the lower spread radius. 
	 * @param String plantName
	 * @return double spreadRadius
	 */
	public static double getLowerRadius(String s) {
		Plant p = Garden.getPlant(s);
		return p.getSpreadRadiusLower();
	}
	
/**
 * Accepts a plant name as a string, and returns the upper spread radius. 
 * @param String plantName
 * @return double spreadRadius
 */
public static double getUpperRadius(String s) {
	Plant p = Garden.getPlant(s);
	return p.getSpreadRadiusUpper();
	}

/**
 * Accepts a plant name as a string, and returns the lower plant size. 
 * @param String plantName
 * @return double lowerSize
 */
public static double getLowerSize(String s) {
	Plant p = Garden.getPlant(s);
	return p.getSizeLower();
}

/**
 * Accpets a plant name as a string, and returns the upper plant size. 
 * @param String plantName
 * @return double upperSize
 */
public static double getUpperSize(String s) {
	Plant p = Garden.getPlant(s);
	return p.getSizeUpper();
}
	
	/**
	 * Accepts two plants, and returns the scientific name of the plant with more leps. 
	 * @param Plant A
	 * @param Plant B
	 * @return String scientificName
	 */
	public static String moreLeps(Plant A, Plant B) {
		if (A.getLepsSupported() > B.getLepsSupported())
			return A.getScientificName();
		else if(B.getLepsSupported() > A.getLepsSupported())
			return B.getScientificName();
		else return "Equal";
	}
	
}