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
}