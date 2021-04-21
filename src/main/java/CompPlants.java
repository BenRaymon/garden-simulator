import java.util.HashMap;

public class CompPlants {
	
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
	
	public static int getLepInfo(String s) {
		Plant p = Garden.getPlant(s);
		return p.getLepsSupported();
	}
	
	public static String moreLeps(Plant A, Plant B) {
		if (A.getLepsSupported() > B.getLepsSupported())
			return A.getScientificName();
		else if(B.getLepsSupported() > A.getLepsSupported())
			return B.getScientificName();
		else return "Equal";
	}
	
}